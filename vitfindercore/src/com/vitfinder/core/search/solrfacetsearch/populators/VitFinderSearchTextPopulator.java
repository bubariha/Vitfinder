/**
 *
 */
package com.vitfinder.core.search.solrfacetsearch.populators;

import de.hybris.platform.catalog.model.classification.ClassificationAttributeModel;
import de.hybris.platform.catalog.model.classification.ClassificationAttributeValueModel;
import de.hybris.platform.classification.ClassificationService;
import de.hybris.platform.classification.features.Feature;
import de.hybris.platform.classification.features.FeatureList;
import de.hybris.platform.classification.features.FeatureValue;
import de.hybris.platform.commerceservices.search.solrfacetsearch.populators.SearchTextPopulator;
import de.hybris.platform.commerceservices.search.solrfacetsearch.querybuilder.FreeTextQueryBuilder;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;
import de.hybris.platform.solrfacetsearch.search.SearchQuery.Operator;
import de.hybris.platform.util.Config;

import org.apache.commons.lang.math.NumberUtils;

import com.vitfinder.core.model.VitfinderGenericProductModel;
import com.vitfinder.core.search.solrfacetsearch.querybuilder.VitFinderFreeTextQueryBuilder;
import com.vitfinder.core.service.VitfinderGenericProductService;


/**
 * @author Nandakishore
 *
 *         Customizing the functionality of solr query builder based on generic product features and OOB functionality
 *
 */
public class VitFinderSearchTextPopulator<FACET_SEARCH_CONFIG_TYPE, INDEXED_TYPE_TYPE, INDEXED_PROPERTY_TYPE, INDEXED_TYPE_SORT_TYPE>
		extends SearchTextPopulator<FACET_SEARCH_CONFIG_TYPE, INDEXED_TYPE_TYPE, INDEXED_PROPERTY_TYPE, INDEXED_TYPE_SORT_TYPE>
{

	private static final String GENERIC_INDEX_PROP = "generic.indexproperty.";
	private VitfinderGenericProductService vitfinderGenericProductService;
	private VitFinderFreeTextQueryBuilder genericFreeTextQueryBuilder;
	private ClassificationService classificationService;

	@Override
	protected void addFreeTextQuery(final SearchQuery searchQuery, final String cleanedFreeTextSearch)
	{
		// Split the full text string to separate words
		final String[] words = cleanedFreeTextSearch.split("\\s+");

		final VitfinderGenericProductModel genericProductModel = getVitfinderGenericProductService()
				.getProductForCode(cleanedFreeTextSearch);
		if (genericProductModel == null)
		{
			for (final FreeTextQueryBuilder freeTextQueryBuilder : getFreeTextQueryBuilders())
			{
				freeTextQueryBuilder.addFreeTextQuery(searchQuery, cleanedFreeTextSearch, words);
			}
		}
		else
		{
			searchQuery.setDefaultOperator(Operator.AND);
			final FeatureList features = classificationService.getFeatures(genericProductModel);
			for (final Feature feature : features.getFeatures())
			{
				final ClassificationAttributeModel classAtrribute = feature.getClassAttributeAssignment()
						.getClassificationAttribute();
				String code = classAtrribute.getCode();
				Object value = feature.getValue() != null ? feature.getValue().getValue() : "";
				String rangeValue = "";
				boolean isFirst = true;
				if (feature.getValues().size() > 1)
				{
					for (final FeatureValue featureValue : feature.getValues())
					{
						final Object range = featureValue.getValue();
						if (range instanceof ClassificationAttributeValueModel)
						{
							if (isFirst)
							{
								rangeValue = "[ " + ((ClassificationAttributeValueModel) range).getName();
							}
							else
							{
								rangeValue = rangeValue + " TO " + ((ClassificationAttributeValueModel) range).getName() + " ]";
							}
							//						featureValueData.setValue(((ClassificationAttributeValueModel) value).getName());
						}
						else if (NumberUtils.isNumber(String.valueOf(range)))
						{
							if (isFirst)
							{
								rangeValue = "[ " + String.valueOf(range).replaceAll("\\.0*$", "");
								//						featureValueData.setValue(String.valueOf(value).replaceAll("\\.0*$", ""));
							}
							else
							{
								rangeValue = rangeValue + " TO " + String.valueOf(range).replaceAll("\\.0*$", "") + "] ";
							}
						}
						else
						{
							if (isFirst)
							{
								rangeValue = "[ " + String.valueOf(range);
								//						featureValueData.setValue(String.valueOf(value));
							}
							else
							{
								rangeValue = rangeValue + " TO " + String.valueOf(range) + " ]";
							}
						}
						isFirst = false;

					}
				}
				if (!rangeValue.isEmpty())
				{
					value = rangeValue;
				}
				if (value == null || (value instanceof String && ((String) value).isEmpty()))
				{
					continue;
				}
				code = Config.getString(GENERIC_INDEX_PROP + code, code);
				getGenericFreeTextQueryBuilder().addFreeTextQuery(searchQuery, value.toString(), code, null);
			}
		}
	}

	public VitFinderFreeTextQueryBuilder getGenericFreeTextQueryBuilder()
	{
		return genericFreeTextQueryBuilder;
	}

	public void setGenericFreeTextQueryBuilder(final VitFinderFreeTextQueryBuilder genericFreeTextQueryBuilder)
	{
		this.genericFreeTextQueryBuilder = genericFreeTextQueryBuilder;
	}


	public ClassificationService getClassificationService()
	{
		return classificationService;
	}

	public void setClassificationService(final ClassificationService classificationService)
	{
		this.classificationService = classificationService;
	}

	/**
	 * @return the vitfinderGenericProductService
	 */
	public VitfinderGenericProductService getVitfinderGenericProductService()
	{
		return vitfinderGenericProductService;
	}

	/**
	 * @param vitfinderGenericProductService
	 *           the vitfinderGenericProductService to set
	 */
	public void setVitfinderGenericProductService(final VitfinderGenericProductService vitfinderGenericProductService)
	{
		this.vitfinderGenericProductService = vitfinderGenericProductService;
	}
}


/**
 *
 */
package com.vitfinder.core.search.solrfacetsearch.querybuilder;



import de.hybris.platform.commerceservices.search.solrfacetsearch.querybuilder.impl.DefaultFreeTextQueryBuilder;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.search.RawQuery;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;
import de.hybris.platform.solrfacetsearch.search.SearchQuery.Operator;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.util.ClientUtils;


/**
 * @author Nandakishore
 *
 */
public class VitFinderFreeTextQueryBuilder extends DefaultFreeTextQueryBuilder
{
	private static final Logger LOG = Logger.getLogger(VitFinderFreeTextQueryBuilder.class);

	@Override
	protected int getBoost()
	{
		// YTODO Auto-generated method stub
		return super.getBoost();
	}

	/**
	 * Getting index properties based on generic product features and building the raw queries to identify the EAN
	 * products
	 *
	 */
	public void addFreeTextQuery(final SearchQuery searchQuery, final String fullText, final String propertyName,
			final String[] textWords)
	{
		final IndexedProperty indexedProperty = searchQuery.getIndexedType().getIndexedProperties().get(propertyName);
		if (indexedProperty != null)
		{
			addFreeTextQuery(searchQuery, indexedProperty, fullText, textWords, getBoost());
		}

	}

	@Override
	protected void addFreeTextQuery(final SearchQuery searchQuery, final IndexedProperty indexedProperty, final String value,
			final double boost)
	{

		final String field = indexedProperty.getName();
		if (!indexedProperty.isFacet())
		{
			if ("text".equalsIgnoreCase(indexedProperty.getType()))
			{
				addFreeTextQuery(searchQuery, field, value.contains("[") ? value : value.toLowerCase(), "", boost);
				addFreeTextQuery(searchQuery, field, value.contains("[") ? value : value.toLowerCase(), "*", boost / 2.0d);
				addFreeTextQuery(searchQuery, field, value.contains("[") ? value : value.toLowerCase(), "~", boost / 4.0d);
			}
			else
			{
				addFreeTextQuery(searchQuery, field, value.contains("[") ? value : value.toLowerCase(), "", boost);
				addFreeTextQuery(searchQuery, field, value.contains("[") ? value : value.toLowerCase(), "*", boost / 2.0d);
			}
		}
		else
		{
			LOG.warn("Not searching " + indexedProperty
					+ ". Free text search not available in facet property. Configure an additional text property for searching.");
		}

	}

	@Override
	protected void addFreeTextQuery(final SearchQuery searchQuery, final String field, final String value, final String suffixOp,
			final double boost)
	{
		@SuppressWarnings("deprecation")
		final RawQuery rawQuery = new RawQuery(field,
				value.contains("[") ? value : ClientUtils.escapeQueryChars(value) + suffixOp + "^" + boost, Operator.OR);
		searchQuery.addRawQuery(rawQuery);
	}
}

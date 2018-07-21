/**
 *
 */
package com.vitfinder.facades.populators;

import de.hybris.platform.classification.ClassificationService;
import de.hybris.platform.classification.features.Feature;
import de.hybris.platform.classification.features.FeatureList;
import de.hybris.platform.commercefacades.order.converters.populator.CartPopulator;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.product.converters.populator.FeaturePopulator;
import de.hybris.platform.commercefacades.product.data.ClassificationData;
import de.hybris.platform.commercefacades.product.data.FeatureData;
import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.retailer.RetailerModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vitfinder.core.BasketSortsData;
import com.vitfinder.core.RetailerData;
import com.vitfinder.core.SafeUpperLimitData;
import com.vitfinder.core.model.BasketSortsModel;


/**
 * @author nandakishore
 *
 */
public class VitfinderCartPopulator extends CartPopulator
{

	private ClassificationService classificationService;

	private FeaturePopulator featurePopulator;

	private FlexibleSearchService flexibleSearchService;

	private BasketSortPopulator basketSortPopulator;

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(final CartModel source, final CartData target) throws ConversionException
	{
		super.populate(source, target);
		updateSafeUpperLimits(source, target);
		updateRetailerData(source, target);
		updatedRetailerBaskets(source, target);
		if (target.getBasketSorts() == null || target.getBasketSorts().isEmpty())
		{
			updateBasketSorts(target);
		}
	}

	/**
	 * @param target
	 */
	private void updateBasketSorts(final CartData target)
	{
		// YTODO Auto-generated method stub
		final List<BasketSortsData> basketSorts = getListOfBasketSorts();
		target.setBasketSorts(basketSorts);

	}

	/**
	 * @return
	 */
	private List<BasketSortsData> getListOfBasketSorts()
	{
		final FlexibleSearchQuery searchQuery = new FlexibleSearchQuery(
				"SELECT {" + BasketSortsModel.PK + "} FROM  {" + BasketSortsModel._TYPECODE + "}");
		searchQuery.setResultClassList(Collections.singletonList(BasketSortsModel.class));
		final SearchResult searchResult = getFlexibleSearchService().search(searchQuery);
		final List<BasketSortsModel> listOfSources = searchResult.getResult();
		final List<BasketSortsData> basketSortsData = new ArrayList<BasketSortsData>();
		if (listOfSources != null && !listOfSources.isEmpty())
		{
			for (final BasketSortsModel basketSort : listOfSources)
			{
				final BasketSortsData sortData = new BasketSortsData();
				getBasketSortPopulator().populate(basketSort, sortData);
				basketSortsData.add(sortData);
			}
		}

		return basketSortsData;
	}

	/**
	 * @param source
	 * @param target
	 */
	private void updateRetailerData(final CartModel source, final CartData target)
	{
		// YTODO Auto-generated method stub
		RetailerData retailer = target.getRetailer();
		if (retailer != null && !source.getRetailer().isEmpty()
				&& retailer.getCode().equalsIgnoreCase(source.getRetailer().isEmpty() ? "" : source.getRetailer().get(0).getCode()))
		{
			//
			return;
		}
		if (!source.getRetailer().isEmpty() && source.getRetailer().size() > 0)
		{
			final RetailerModel retailerModel = source.getRetailer().get(0);
			retailer = new RetailerData();
			retailer.setCode(retailerModel.getCode());
			retailer.setDescription(retailerModel.getDescription());
			retailer.setName(retailerModel.getName());
			final MediaModel retailerMedia = retailerModel.getThumbnail();
			final ImageData imageData = new ImageData();
			if (retailerMedia != null)
			{
				imageData.setUrl(retailerMedia.getURL());
				imageData.setFormat(retailerMedia.getMediaFormat() != null ? retailerMedia.getMediaFormat().getName() : "");
			}
			retailer.setThumbnail(imageData);
		}
		target.setRetailer(retailer);
	}


	/**
	 * @param source
	 * @param target
	 */
	private void updatedRetailerBaskets(final CartModel source, final CartData target)
	{
		// YTODO Auto-generated method stub
		List<CartData> retailerBaskets = target.getRetailerBaskets();
		if (retailerBaskets == null)
		{
			retailerBaskets = new ArrayList<CartData>();
		}
		final List<String> retailerCartIds = new ArrayList<String>();
		for (final CartData retailerBasket : retailerBaskets)
		{
			retailerCartIds.add(retailerBasket.getCode());
		}
		if (source.getRetailerBaskets() != null && source.getRetailerBaskets().size() > 0)
		{
			for (final CartModel retailerBasket : source.getRetailerBaskets())
			{
				if (!retailerCartIds.contains(retailerBasket.getCode()))
				{
					final CartData retailerTarget = new CartData();
					super.populate(retailerBasket, retailerTarget);
					updateRetailerData(retailerBasket, retailerTarget);
					updateSafeUpperLimits(retailerBasket, retailerTarget);
					retailerBaskets.add(retailerTarget);
				}
			}
		}
		target.setRetailerBaskets(retailerBaskets);
	}

	/**
	 * @param source
	 * @param target
	 */
	private void updateSafeUpperLimits(final CartModel cartModel, final CartData target)
	{
		// YTODO Auto-generated method stub
		final Map<String, SafeUpperLimitData> nutritionData = new HashMap<String, SafeUpperLimitData>();
		for (final AbstractOrderEntryModel entry : cartModel.getEntries())
		{
			final FeatureList featureList = getClassificationService().getFeatures(entry.getProduct());
			final List<ClassificationData> result = new ArrayList<ClassificationData>();
			final Map<String, ClassificationData> map = new HashMap<String, ClassificationData>();

			for (final Feature feature : featureList.getFeatures())
			{
				if (feature.getValues() != null && !feature.getValues().isEmpty())
				{
					if (nutritionData.containsKey(feature.getClassAttributeAssignment().getClassificationAttribute().getCode()))
					{
						final SafeUpperLimitData ingredient = nutritionData
								.get(feature.getClassAttributeAssignment().getClassificationAttribute().getCode());
						ingredient.setCartQty(ingredient.getCartQty()
								+ (entry.getQuantity() * Double.valueOf(feature.getValue().getValue().toString())));
					}
					else
					{
						final SafeUpperLimitData safeUpperLimit = new SafeUpperLimitData();
						final FeatureData featureData = new FeatureData();
						getFeaturePopulator().populate(feature, featureData);
						safeUpperLimit.setIngredient(featureData);
						safeUpperLimit.setCartQty(Double.valueOf(feature.getValue().getValue().toString()) * entry.getQuantity());
						safeUpperLimit.setSafeUpperLimit(
								feature.getClassAttributeAssignment().getClassificationAttribute().getSafeUpperLimit());
						nutritionData.put(feature.getClassAttributeAssignment().getClassificationAttribute().getCode(), safeUpperLimit);
					}
				}
			}

		}

		final List<SafeUpperLimitData> data = new ArrayList<SafeUpperLimitData>();
		data.addAll(nutritionData.values());
		target.setSafeUpperLimits(data);
	}

	/**
	 * @return the classificationService
	 */
	public ClassificationService getClassificationService()
	{
		return classificationService;
	}

	/**
	 * @param classificationService
	 *           the classificationService to set
	 */
	public void setClassificationService(final ClassificationService classificationService)
	{
		this.classificationService = classificationService;
	}

	/**
	 * @return the featurePopulator
	 */
	public FeaturePopulator getFeaturePopulator()
	{
		return featurePopulator;
	}

	/**
	 * @param featurePopulator
	 *           the featurePopulator to set
	 */
	public void setFeaturePopulator(final FeaturePopulator featurePopulator)
	{
		this.featurePopulator = featurePopulator;
	}

	/**
	 * @return the flexibleSearchService
	 */
	public FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}

	/**
	 * @param flexibleSearchService
	 *           the flexibleSearchService to set
	 */
	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}

	/**
	 * @return the basketSortPopulator
	 */
	public BasketSortPopulator getBasketSortPopulator()
	{
		return basketSortPopulator;
	}

	/**
	 * @param basketSortPopulator
	 *           the basketSortPopulator to set
	 */
	public void setBasketSortPopulator(final BasketSortPopulator basketSortPopulator)
	{
		this.basketSortPopulator = basketSortPopulator;
	}

}

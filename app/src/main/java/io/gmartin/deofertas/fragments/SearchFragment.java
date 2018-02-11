package io.gmartin.deofertas.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.gmartin.deofertas.R;
import io.gmartin.deofertas.controllers.BaseController;
import io.gmartin.deofertas.controllers.SearchController;
import io.gmartin.deofertas.models.Search;
import io.gmartin.deofertas.models.Store;
import io.gmartin.deofertas.widget.MultiSelectionSpinner;

public class SearchFragment extends Fragment implements BaseController.BaseControllerListener{

    public final static String EXTRA_SEARCH = "io.gmartin.deofertas.activities.SEARCH";
    private SearchView mSearchBox;
    private AppCompatEditText mPriceFrom;
    private AppCompatEditText mPriceTo;
    private AppCompatButton mSearchBtn;
    private AppCompatButton mAdvSearchBtn;
    private Search mSearch;
    private MultiSelectionSpinner mStoreSpinner;
    private LinearLayoutCompat mAdvancedSearchLayout;
    private boolean mIsAdvancedSearch = false;
    private List<Store> mStoreList;
    private SearchController mSearchController;
    private Context mContext;
    private OnSearchInteractionListener mListener;
    private View mRoot;

    public interface OnSearchInteractionListener {
        void onSearchButtonClick(Search search);
    }

    public SearchFragment(){}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

        if (mContext instanceof OnSearchInteractionListener) {
            mListener = (OnSearchInteractionListener) mContext;
        } else {
            throw new RuntimeException(mContext.toString()
                    + " must implement OnSearchInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSearchController = new SearchController(this.getActivity(), this);
        mSearchController.fetchStores();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_search, container, false);

        mSearchBox = mRoot.findViewById(R.id.search_box);
        mSearchBox.setSubmitButtonEnabled(true);
        mPriceFrom = mRoot.findViewById(R.id.edit_price_from);
        mPriceTo = mRoot.findViewById(R.id.edit_price_to);
        mSearchBtn = mRoot.findViewById(R.id.button_search);
        mAdvSearchBtn = mRoot.findViewById(R.id.button_advanced_search);
        mAdvancedSearchLayout = mRoot.findViewById(R.id.advanced_search_layout);
        mStoreSpinner = mRoot.findViewById(R.id.store_spinner);

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchOffers();
            }
        });

        mAdvSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                advancedSearchOffers();
            }
        });

        return mRoot;
    }

    private void searchOffers() {
        Double priceFrom = null;
        Double priceTo = null;
        List<Store> storesSelected;
        List<Integer> storesIndexes;
        mSearch = new Search();

        mSearch.setText(mSearchBox.getQuery().toString());

        if (mPriceFrom.getText().toString().length() > 0) {
            priceFrom = Double.valueOf(mPriceFrom.getText().toString());
            mSearch.setPriceFrom(priceFrom);
        }

        if (mPriceTo.getText().toString().length() > 0) {
            priceTo = Double.valueOf(mPriceTo.getText().toString());
            mSearch.setPriceTo(priceTo);
        }

        if (mStoreSpinner.getCountItemsSelected() > 0) {
            storesIndexes = mStoreSpinner.getSelectedIndexes();

            storesSelected = new ArrayList<>();

            for (Integer index : storesIndexes) {
                storesSelected.add(mStoreList.get(index));
            }

            mSearch.setStores(storesSelected);
        }

        if (priceFrom != null && priceTo != null && priceFrom > priceTo) {
            Toast.makeText(mContext, getResources().getString(R.string.price_from_greater_to), Toast.LENGTH_LONG).show();
        } else {
            mListener.onSearchButtonClick(mSearch);
        }
    }

    private void advancedSearchOffers() {
        if (mIsAdvancedSearch) {
            mAdvSearchBtn.setText(getResources().getString(R.string.button_advanced_search_text));
            mAdvancedSearchLayout.setVisibility(View.GONE);
            mPriceFrom.setText(null);
            mPriceTo.setText(null);
        } else {
            mAdvSearchBtn.setText(getResources().getString(R.string.button_basic_search_text));
            mAdvancedSearchLayout.setVisibility(View.VISIBLE);
        }

        mIsAdvancedSearch = !mIsAdvancedSearch;
    }

    @Override
    public void onDataReceived(Object data) {
        if (data != null) {
            mStoreList = (List<Store>) data;
            mStoreSpinner.setItems(mSearchController.storeToString(mStoreList));
        }
    }
}

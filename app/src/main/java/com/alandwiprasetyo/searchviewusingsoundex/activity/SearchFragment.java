package com.alandwiprasetyo.searchviewusingsoundex.activity;

/**
 * Created by root on 19/07/16.
 */

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alandwiprasetyo.searchviewusingsoundex.R;
import com.alandwiprasetyo.searchviewusingsoundex.utils.RecyclerItemClickListener;
import com.alandwiprasetyo.searchviewusingsoundex.adapter.SwipeListAdapter;
import com.alandwiprasetyo.searchviewusingsoundex.model.DummyData;
import com.alandwiprasetyo.searchviewusingsoundex.model.Word;

import java.util.List;

/**
 * Created by root on 25/12/15.
 */
public class SearchFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,SearchView.OnQueryTextListener,
        SearchView.OnCloseListener {
    private SearchView mSearchView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipeListAdapter adapter;
    private List<Word> wordList;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //using action bar menu
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu_list, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        wordList = DummyData.dummy();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        adapter = new SwipeListAdapter(getActivity(), wordList);
        mRecyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        if(wordList.isEmpty()){
            swipeRefreshLayout.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            onRefresh();
                                        }
                                    }
            );
        }
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever
                        toast(position);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                        toast(position);
                    }
                    public void toast(int position){
                        Toast.makeText(getActivity(),wordList.get(position).name,Toast.LENGTH_SHORT).show();
                    }
                })
        );
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        setupSearchView();
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(true);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();

            // Try to use the "applications" global search provider
            SearchableInfo info = searchManager.getSearchableInfo(getActivity().getComponentName());
            for (SearchableInfo inf : searchables) {
                if (inf.getSuggestAuthority() != null
                        && inf.getSuggestAuthority().startsWith("applications")) {
                    info = inf;
                }
            }
            mSearchView.setSearchableInfo(info);
        }

        mSearchView.setOnQueryTextListener(this);
        mSearchView.setOnCloseListener(this);
    }

    @Override
    public boolean onClose() {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        adapter.getFilter().filter(query.toString());
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText.toString());
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        swipeRefreshLayout.setRefreshing(false);
    }
}
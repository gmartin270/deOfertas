package io.gmartin.deofertas.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import io.gmartin.deofertas.R;
import io.gmartin.deofertas.activities.ResultsActivity;
import io.gmartin.deofertas.adapters.ItemAdapter;
import io.gmartin.deofertas.models.Item;


public class ListFragment extends Fragment {
    /*// TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ListFragment() {
        // Required empty public constructor
    }

    *//**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     *//*
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    */

    private View mRoot;
    private OnOffersListInteractionListener mListener;
    private Context mContext;

    public interface OnOffersListInteractionListener {
        void onSelectedItem(Object item);
    }

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*GetListTask task = new GetListTask();
        task.execute("http://tm5-agmoyano.rhcloud.com/");*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

        if (mContext instanceof OnOffersListInteractionListener) {
            mListener = (OnOffersListInteractionListener) mContext;
        } else {
            throw new RuntimeException(mContext.toString()
                    + " must implement OnOffersListInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /*if (mAdapter == null) {
            mAdapter = new ItemAdapter(getActivity());
            //TODO: remove hardcode list and replace with a real data provided by a service.
            List<Item> items = new ArrayList<Item>();

            Item item = new Item();
            item.setId(1);
            item.setDesc("Prueba 1");
            item.setStore("Garbarino");
            item.setPrice(100);

            items.add(item);

            item = new Item();
            item.setId(1);
            item.setDesc("Prueba 2");
            item.setStore("Musimundo");
            item.setPrice(350);

            items.add(item);

            mAdapter.setItemList(items);
        }*/

        mRoot = inflater.inflate(R.layout.fragment_list, container, false);
        //Button fab = (Button) root.findViewById(R.id.btnAdd);

        /*if (((ResultsActivity)getActivity()).getIsPort()) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        ((MainActivity) getActivity()).selectUser(null, null);
                    }catch (Exception e){

                    }
                }
            });
        } else {
            fab.setVisibility(View.INVISIBLE);
        }*/

        ListView list = (ListView) mRoot.findViewById(R.id.listOffers);
        list.setAdapter(ItemAdapter.getInstance(mContext));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    mListener.onSelectedItem(ItemAdapter.getInstance(mContext).getItem(position));
                }catch(Exception e){

                }
            }
        });

        return mRoot;
    }

    /*public class GetListTask extends AsyncTask<String, Integer, List<Persona>> {
        @Override
        protected List<Persona> doInBackground(String... urlString) {
            List<Persona> personas = null;
            InputStream is = null;

            try {
                URL url = new URL(urlString[0].toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 *//* milliseconds *//*);
                conn.setConnectTimeout(15000 *//* milliseconds *//*);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                int response = conn.getResponseCode();
                //Log.d(DEBUG_TAG, "The response is: " + response);
                is = conn.getInputStream();

                // Convert the InputStream into a string
                String contentAsString = readIt(is);
                personas = JSonArrayToPersonas(contentAsString);
                //return contentAsString;

            }catch (Exception e){

            }
            return personas;
        }

        private String readIt(InputStream is) throws Exception{
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }

            return total.toString();
        }

        private List<Persona> JSonArrayToPersonas(String json) throws JSONException {
            List<Persona> personas = new ArrayList<Persona>();
            JSONArray jsonArray =  new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject explrObject = jsonArray.getJSONObject(i);

                Persona persona = new Persona(explrObject.getString("nombre"), explrObject.getString("apellido"), explrObject.getString("mail"));
                personas.add(persona);
            }

            return personas;
        }

        @Override
        protected void onPostExecute(List<Item> items) {
            mAdapter.setItemList(items);
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}

package joseocampo.VTSBD.com;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.RecoverySystem;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentRegistrarUsuario.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentRegistrarUsuario#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRegistrarUsuario extends Fragment
        implements Response.Listener<JSONObject>, Response.ErrorListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button btnRegistrar;
    private EditText campoDocumento, campoNombre, campoProfesion;
    private ProgressDialog barraProgreso;

    //estos objetos request nos permiten establecer la conexion con el servidor de bd.
    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    private OnFragmentInteractionListener mListener;

    public FragmentRegistrarUsuario() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentRegistrarUsuario.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentRegistrarUsuario newInstance(String param1, String param2) {
        FragmentRegistrarUsuario fragment = new FragmentRegistrarUsuario();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_fragment_registrar_usuario, container, false);

        campoDocumento = (EditText) vista.findViewById(R.id.campoDocumento);
        campoNombre = (EditText) vista.findViewById(R.id.campoNombre);
        campoProfesion = (EditText) vista.findViewById(R.id.campoProfesion);
        btnRegistrar = (Button) vista.findViewById(R.id.btnRegistrar);

        request = Volley.newRequestQueue(getContext());

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService();
            }
        });

        return vista;

    }

    private void cargarWebService() {
        barraProgreso = new ProgressDialog(getContext());
        barraProgreso.setMessage("cargando...");
        barraProgreso.show();

        String url = "http://vtsmsph.com/ejemploAndroid/wsJSONRegistro.php?" +
                "documento=" + campoDocumento.getText().toString() +
                "&nombre=" + campoNombre.getText().toString() +
                "&profesion=" + campoProfesion.getText().toString();

        //esto hace que permita ingresar los datos con espacios, ejemplo: Didier Jose
        url.replace(" ", "%20");

        //esto nos permite establecer comunicacion con los metodos onErrorResponse() y onResponse().
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);

    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getContext(), "Se ha registrado con exito", Toast.LENGTH_SHORT).show();
        barraProgreso.hide();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        barraProgreso.hide();
        Toast.makeText(getContext(), "Error al registrar "+error.toString(), Toast.LENGTH_LONG).show();
        Log.i("Error",error.toString());
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

package Example;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.laioffer.matrix.EventAdapter;
import com.laioffer.matrix.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Example_CommentFragment extends Fragment {

    private GridView gridView;

    OnItemSelectListener callback;
    // Container Activity must implement this interface
    public interface OnItemSelectListener {
        public void onCommentSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (OnItemSelectListener) context;
        } catch (ClassCastException e) {
            //do something
        }
    }

    public Example_CommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        gridView = view.findViewById(R.id.comment_grid);
        gridView.setAdapter(new EventAdapter(getActivity()));

        // add click listen to grid item
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                callback.onCommentSelected(position);
            }
        });

        return view;
    }

    public void onItemSelected(int position){
        for (int i = 0; i < gridView.getChildCount(); i++){
            if (position == i) {
                gridView.getChildAt(i).setBackgroundColor(Color.BLUE);
            } else {
                gridView.getChildAt(i).setBackgroundColor(Color.parseColor("#FAFAFA"));
            }
        }
    }


}

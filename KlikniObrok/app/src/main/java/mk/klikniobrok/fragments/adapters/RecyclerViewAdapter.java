package mk.klikniobrok.fragments.adapters;

import android.support.v4.util.Pair;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mk.klikniobrok.R;

import java.util.List;

/**
 * Created by gjorgjim on 12/4/16.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Pair<String, String>> array;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ViewHolder(View view) {
            super(view);
            this.view = view;
        }
    }

    public RecyclerViewAdapter(List<Pair<String, String>> array) {
       // this.array = new ArrayList<>();
        this.array = array;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        AppCompatTextView title = (AppCompatTextView) holder.view.findViewById(R.id.recycerViewTitle);
        AppCompatTextView address = (AppCompatTextView) holder.view.findViewById(R.id.recuclerViewAddress);
        title.setText(array.get(position).first);
        address.setText(array.get(position).second);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return array.size();
    }
}

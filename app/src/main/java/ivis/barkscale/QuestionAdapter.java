package ivis.barkscale;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wizard on 2018. 4. 12..
 */

public class QuestionAdapter extends ArrayAdapter<Question> {

    public QuestionAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Question> objects) {
        super(context, resource, textViewResourceId, objects);
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = super.getView(position, convertView, parent);
        TextView textView = itemView.findViewById(R.id.lblQuestion);

        Question question = getItem(position);
        textView.setText(question.str);
        return  itemView;
    }
}

package rawe.gordon.com.boucingballdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by gordon on 2015/8/1.
 */
public class SegmentAdapter extends ArrayAdapter<User> {
    private static class ViewHolder {
        TextView userName;
        TextView passWord;
    }

    public SegmentAdapter(Context context, List<User> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = getItem(position);
        ViewHolder viewHolder;
        if(convertView==null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user,null);
            viewHolder.userName = (TextView)convertView.findViewById(R.id.username);
            viewHolder.passWord = (TextView)convertView.findViewById(R.id.password);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.userName.setText(user.getUserName());
        viewHolder.passWord.setText(user.getPassWord());
        return convertView;
    }
}

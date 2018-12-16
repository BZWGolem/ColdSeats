package pl.minun.testseats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends BaseAdapter {
    private final List<Item> mItems = new ArrayList<Item>();
    private FloorPlan floorPlan;
    private final LayoutInflater mInflater;

    public GridAdapter(Context context, FloorPlan f) {
        mInflater = LayoutInflater.from(context);
        floorPlan = f;

        for(String panel : floorPlan.shape){
            switch(floorPlan.deskToStatusMap.get(panel) ){
                case "FREE":
                    mItems.add(new Item(panel,       R.drawable.green_rect));
                    break;
                case "EMPTY":
                    mItems.add(new Item(null,       R.drawable.white_rect));
                    break;
                case "RESERVED":
                    mItems.add(new Item(panel,       R.drawable.yellow_rect));
                    break;
                case "TAKEN":
                    mItems.add(new Item(panel,       R.drawable.red_rect));
                    break;
                case "MY_TAKEN":
                    mItems.add(new Item(panel,       R.drawable.red_rect_mine));
                    break;
                case "MY_RESERVED":
                    mItems.add(new Item(panel,       R.drawable.yellow_rect_mine));
                    break;
                 default:
                     mItems.add(new Item(null,       R.drawable.black_rect));
                     break;

            }
        }
/*        mItems.add(new Item("name",       R.drawable.white_rect));
        mItems.add(new Item(null,   R.drawable.blue_rect));
        mItems.add(new Item(null, R.drawable.black_rect));
        mItems.add(new Item(null,      R.drawable.white_rect));
        mItems.add(new Item(null,     R.drawable.green_rect));
        mItems.add(new Item(null,      R.drawable.yellow_rect));
        mItems.add(new Item(null,      R.drawable.red_rect));
        mItems.add(new Item(null,       R.drawable.white_rect));
        mItems.add(new Item(null,   R.drawable.blue_rect));
        mItems.add(new Item(null, R.drawable.black_rect));
        mItems.add(new Item(null,      R.drawable.white_rect));
        mItems.add(new Item(null,     R.drawable.green_rect));
        mItems.add(new Item(null,      R.drawable.yellow_rect));
        mItems.add(new Item(null,      R.drawable.red_rect));
        mItems.add(new Item(null,       R.drawable.white_rect));
        mItems.add(new Item(null,   R.drawable.blue_rect));
        mItems.add(new Item(null, R.drawable.black_rect));
        mItems.add(new Item(null,      R.drawable.white_rect));
        mItems.add(new Item(null,     R.drawable.green_rect));
        mItems.add(new Item(null,      R.drawable.yellow_rect));
        mItems.add(new Item(null,      R.drawable.red_rect));
        mItems.add(new Item(null,       R.drawable.white_rect));
        mItems.add(new Item(null,   R.drawable.blue_rect));
        mItems.add(new Item(null, R.drawable.black_rect));
        mItems.add(new Item(null,      R.drawable.white_rect));
        mItems.add(new Item(null,     R.drawable.green_rect));
        mItems.add(new Item(null,      R.drawable.yellow_rect));
        mItems.add(new Item(null,      R.drawable.red_rect));
        mItems.add(new Item(null,       R.drawable.white_rect));
        mItems.add(new Item(null,   R.drawable.blue_rect));
        mItems.add(new Item(null, R.drawable.black_rect));
        mItems.add(new Item(null,      R.drawable.white_rect));
        mItems.add(new Item(null,     R.drawable.green_rect));
        mItems.add(new Item(null,      R.drawable.yellow_rect));
        mItems.add(new Item(null,      R.drawable.red_rect));
        mItems.add(new Item(null,       R.drawable.white_rect));
        mItems.add(new Item(null,   R.drawable.blue_rect));
        mItems.add(new Item(null, R.drawable.black_rect));
        mItems.add(new Item(null,      R.drawable.white_rect));
        mItems.add(new Item(null,     R.drawable.green_rect));
        mItems.add(new Item(null,      R.drawable.yellow_rect));
        mItems.add(new Item(null,      R.drawable.red_rect));
        mItems.add(new Item(null,       R.drawable.white_rect));
        mItems.add(new Item(null,   R.drawable.blue_rect));
        mItems.add(new Item(null, R.drawable.black_rect));
        mItems.add(new Item(null,      R.drawable.white_rect));
        mItems.add(new Item(null,     R.drawable.green_rect));
        mItems.add(new Item(null,      R.drawable.yellow_rect));
        mItems.add(new Item(null,      R.drawable.red_rect));
        mItems.add(new Item(null,       R.drawable.white_rect));
        mItems.add(new Item(null,   R.drawable.blue_rect));
        mItems.add(new Item(null, R.drawable.black_rect));
        mItems.add(new Item(null,      R.drawable.white_rect));
        mItems.add(new Item(null,     R.drawable.green_rect));
        mItems.add(new Item(null,      R.drawable.yellow_rect));
        mItems.add(new Item(null,      R.drawable.red_rect));
        mItems.add(new Item(null,       R.drawable.white_rect));
        mItems.add(new Item(null,   R.drawable.blue_rect));
        mItems.add(new Item(null, R.drawable.black_rect));
        mItems.add(new Item(null,      R.drawable.white_rect));
        mItems.add(new Item(null,     R.drawable.green_rect));
        mItems.add(new Item(null,      R.drawable.yellow_rect));
        mItems.add(new Item(null,      R.drawable.red_rect));
        mItems.add(new Item(null,       R.drawable.white_rect));
        mItems.add(new Item(null,   R.drawable.blue_rect));
        mItems.add(new Item(null, R.drawable.black_rect));
        mItems.add(new Item(null,      R.drawable.white_rect));
        mItems.add(new Item(null,     R.drawable.green_rect));
        mItems.add(new Item(null,      R.drawable.yellow_rect));
        mItems.add(new Item(null,      R.drawable.red_rect));
        mItems.add(new Item(null,       R.drawable.white_rect));
        mItems.add(new Item(null,   R.drawable.blue_rect));
        mItems.add(new Item(null, R.drawable.black_rect));
        mItems.add(new Item(null,      R.drawable.white_rect));
        mItems.add(new Item(null,     R.drawable.green_rect));
        mItems.add(new Item(null,      R.drawable.yellow_rect));
        mItems.add(new Item(null,      R.drawable.red_rect));
        mItems.add(new Item(null,       R.drawable.white_rect));
        mItems.add(new Item(null,   R.drawable.blue_rect));
        mItems.add(new Item(null, R.drawable.black_rect));
        mItems.add(new Item(null,      R.drawable.white_rect));
        mItems.add(new Item(null,     R.drawable.green_rect));
        mItems.add(new Item(null,      R.drawable.yellow_rect));
        mItems.add(new Item(null,      R.drawable.red_rect));
        mItems.add(new Item(null,       R.drawable.white_rect));
        mItems.add(new Item(null,   R.drawable.blue_rect));
        mItems.add(new Item(null, R.drawable.black_rect));
        mItems.add(new Item(null,      R.drawable.white_rect));
        mItems.add(new Item(null,     R.drawable.green_rect));
        mItems.add(new Item(null,      R.drawable.yellow_rect));
        mItems.add(new Item(null,      R.drawable.red_rect));
        mItems.add(new Item(null,       R.drawable.white_rect));
        mItems.add(new Item(null,   R.drawable.blue_rect));
        mItems.add(new Item(null, R.drawable.black_rect));
        mItems.add(new Item(null,      R.drawable.white_rect));
        mItems.add(new Item(null,     R.drawable.green_rect));
        mItems.add(new Item(null,      R.drawable.yellow_rect));
        mItems.add(new Item(null,      R.drawable.red_rect));
        mItems.add(new Item(null,       R.drawable.white_rect));
        mItems.add(new Item(null,   R.drawable.blue_rect));
        mItems.add(new Item(null, R.drawable.black_rect));
        mItems.add(new Item(null,      R.drawable.white_rect));
        mItems.add(new Item(null,     R.drawable.green_rect));
        mItems.add(new Item(null,      R.drawable.yellow_rect));
        mItems.add(new Item(null,      R.drawable.red_rect));
        mItems.add(new Item(null,       R.drawable.white_rect));
        mItems.add(new Item(null,   R.drawable.blue_rect));
        mItems.add(new Item(null, R.drawable.black_rect));
        mItems.add(new Item(null,      R.drawable.white_rect));
        mItems.add(new Item(null,     R.drawable.green_rect));
        mItems.add(new Item(null,      R.drawable.yellow_rect));
        mItems.add(new Item(null,      R.drawable.red_rect));
        mItems.add(new Item(null,       R.drawable.white_rect));
        mItems.add(new Item(null,   R.drawable.blue_rect));
        mItems.add(new Item(null, R.drawable.black_rect));
        mItems.add(new Item(null,      R.drawable.white_rect));
        mItems.add(new Item(null,     R.drawable.green_rect));
        mItems.add(new Item(null,      R.drawable.yellow_rect));
        mItems.add(new Item(null,      R.drawable.red_rect));
        mItems.add(new Item(null,       R.drawable.white_rect));
        mItems.add(new Item(null,   R.drawable.blue_rect));
        mItems.add(new Item(null, R.drawable.black_rect));
        mItems.add(new Item(null,      R.drawable.white_rect));
        mItems.add(new Item(null,     R.drawable.green_rect));
        mItems.add(new Item(null,      R.drawable.yellow_rect));
        mItems.add(new Item(null,      R.drawable.red_rect));
        mItems.add(new Item(null,       R.drawable.white_rect));
        mItems.add(new Item(null,   R.drawable.blue_rect));
        mItems.add(new Item(null, R.drawable.black_rect));
        mItems.add(new Item(null,      R.drawable.white_rect));
        mItems.add(new Item(null,     R.drawable.green_rect));
        mItems.add(new Item(null,      R.drawable.yellow_rect));
        mItems.add(new Item(null,      R.drawable.red_rect));
        mItems.add(new Item(null,       R.drawable.white_rect));
        mItems.add(new Item(null,   R.drawable.blue_rect));
        mItems.add(new Item(null, R.drawable.black_rect));
        mItems.add(new Item(null,      R.drawable.white_rect));
        mItems.add(new Item(null,     R.drawable.green_rect));
        mItems.add(new Item(null,      R.drawable.yellow_rect));
        mItems.add(new Item(null,      R.drawable.red_rect));
        mItems.add(new Item(null,       R.drawable.white_rect));
        mItems.add(new Item(null,   R.drawable.blue_rect));
        mItems.add(new Item(null, R.drawable.black_rect));
        mItems.add(new Item(null,      R.drawable.white_rect));
        mItems.add(new Item(null,     R.drawable.green_rect));
        mItems.add(new Item(null,      R.drawable.yellow_rect));
        mItems.add(new Item(null,      R.drawable.red_rect));
        mItems.add(new Item(null,       R.drawable.white_rect));
        mItems.add(new Item(null,   R.drawable.blue_rect));
        mItems.add(new Item(null, R.drawable.black_rect));
        mItems.add(new Item(null,      R.drawable.white_rect));
        mItems.add(new Item(null,     R.drawable.green_rect));
        mItems.add(new Item(null,      R.drawable.yellow_rect));
        mItems.add(new Item(null,      R.drawable.red_rect));
        mItems.add(new Item(null,       R.drawable.white_rect));
        mItems.add(new Item(null,   R.drawable.blue_rect));
        mItems.add(new Item(null, R.drawable.black_rect));
        mItems.add(new Item(null,      R.drawable.white_rect));
        mItems.add(new Item(null,     R.drawable.green_rect));
        mItems.add(new Item(null,      R.drawable.yellow_rect));
        mItems.add(new Item(null,      R.drawable.red_rect));
        mItems.add(new Item(null,       R.drawable.white_rect));
        mItems.add(new Item(null,   R.drawable.blue_rect));
        mItems.add(new Item(null, R.drawable.black_rect));
        mItems.add(new Item(null,      R.drawable.white_rect));
        mItems.add(new Item(null,     R.drawable.green_rect));
        mItems.add(new Item(null,      R.drawable.yellow_rect));
        mItems.add(new Item(null,      R.drawable.red_rect));
        mItems.add(new Item(null,       R.drawable.white_rect));
        mItems.add(new Item(null,   R.drawable.blue_rect));
        mItems.add(new Item(null, R.drawable.black_rect));
        mItems.add(new Item(null,      R.drawable.white_rect));
        mItems.add(new Item(null,     R.drawable.green_rect));
        mItems.add(new Item(null,      R.drawable.yellow_rect));
        mItems.add(new Item(null,      R.drawable.red_rect));
        mItems.add(new Item(null,       R.drawable.white_rect));
        mItems.add(new Item(null,   R.drawable.blue_rect));
        mItems.add(new Item(null, R.drawable.black_rect));
        mItems.add(new Item(null,      R.drawable.white_rect));
        mItems.add(new Item(null,     R.drawable.green_rect));
        mItems.add(new Item(null,      R.drawable.yellow_rect));
        mItems.add(new Item(null,      R.drawable.red_rect));
        mItems.add(new Item(null,       R.drawable.white_rect));
        mItems.add(new Item(null,   R.drawable.blue_rect));
        mItems.add(new Item(null, R.drawable.black_rect));
        mItems.add(new Item(null,      R.drawable.white_rect));
        mItems.add(new Item(null,     R.drawable.green_rect));
        mItems.add(new Item(null,      R.drawable.yellow_rect));
        mItems.add(new Item(null,      R.drawable.red_rect));
        mItems.add(new Item(null,       R.drawable.white_rect));
        mItems.add(new Item(null,   R.drawable.blue_rect));
        mItems.add(new Item(null, R.drawable.black_rect));
        mItems.add(new Item(null,      R.drawable.white_rect));
        mItems.add(new Item(null,     R.drawable.green_rect));
        mItems.add(new Item(null,      R.drawable.yellow_rect));
        mItems.add(new Item(null,      R.drawable.red_rect));*/
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Item getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mItems.get(i).drawableId;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        ImageView picture;
        //TextView name;

        if (v == null) {
            v = mInflater.inflate(R.layout.grid_item, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
            v.setTag(R.id.text, v.findViewById(R.id.text));
        }

        picture = (ImageView) v.getTag(R.id.picture);
        //name = (TextView) v.getTag(R.id.text);

        Item item = getItem(i);

        picture.setImageResource(item.drawableId);
        //name.setText(item.name);

        return v;
    }

    private static class Item {
        public final String name;
        public final int drawableId;

        Item(String name, int drawableId) {
            this.name = name;
            this.drawableId = drawableId;
        }
    }
}
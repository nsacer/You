package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.example.administrator.you.R;

import java.util.ArrayList;
import java.util.List;

import helper.CardAdapterHelper;

/**
 * Created by Administrator on 2017/9/9.
 * CardAdapter
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder>
        implements View.OnClickListener{

    private Context mContext;
    private CardAdapterHelper cardAdapterHelper = new CardAdapterHelper();
    private OnCardItemClickListener onCardItemClickListener;
    private int[] drawableIds = new int[]{
            R.mipmap.mv1,
            R.mipmap.mv2,
            R.mipmap.mv3,
            R.mipmap.mv4,
            R.mipmap.mv5,
            R.mipmap.mv6,
            R.mipmap.mv7,
            R.mipmap.mv8,
            R.mipmap.mv9,
    };
    private List<Integer> clickIntegers = new ArrayList<>();

    public CardAdapter(Context context) {

        this.mContext = context;
    }

    @Override
    public void onClick(View view) {

        if (onCardItemClickListener != null) {

            int currentIndex = (int) view.getTag(R.id.cd_item_card);
            if (!clickIntegers.contains(currentIndex))
                clickIntegers.add(currentIndex);
            ViewSwitcher vs = (ViewSwitcher) view.getTag(R.id.is_card_item);
            onCardItemClickListener.OnItemClick(view, currentIndex, vs);
        }
    }

    public interface OnCardItemClickListener{

        void OnItemClick(View view, int position, ViewSwitcher vs);
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        view.setOnClickListener(this);
        cardAdapterHelper.onCreateViewHolder(parent, view);
        CardViewHolder holder = new CardViewHolder(view);
        holder.setIsRecyclable(false);
        return holder;
    }

    @Override
    public void onBindViewHolder(final CardViewHolder holder, int position) {



        holder.itemView.setTag(R.id.cd_item_card, position);
        holder.itemView.setTag(R.id.is_card_item, holder.vs);
        holder.iv.setImageResource(drawableIds[position]);

        if (!clickIntegers.contains(position))
            holder.vs.setDisplayedChild(0);
        else
            holder.vs.setDisplayedChild(1);


        cardAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());
    }

    @Override
    public int getItemCount() {
        return drawableIds.length;
    }

    class CardViewHolder extends RecyclerView.ViewHolder {

        private ViewSwitcher vs;
        private ImageView iv;

        CardViewHolder(View itemView) {
            super(itemView);

            vs = (ViewSwitcher) itemView.findViewById(R.id.is_card_item);
            iv = (ImageView) itemView.findViewById(R.id.iv_card_item);
        }

    }

    public void setOnCardItemClickListener(OnCardItemClickListener onCardItemClickListener) {
        this.onCardItemClickListener = onCardItemClickListener;
    }

}

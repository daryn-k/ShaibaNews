package kz.shaiba.shaibanews.RetrieveFeed;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kz.shaiba.shaibanews.PostPage;
import kz.shaiba.shaibanews.R;
import kz.shaiba.shaibanews.DateUtils;
import kz.shaiba.shaibanews.UrlImageViewHelper.UrlImageViewHelper;

public class RSSAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<RSSItemData> news_items;
    Activity ctx;

    private static final int NEWS_WITH_IMAGE = 1;
    private static final int NEWS_WITHOUT_IMAGE = 2;

    //** Конструктор класса **//

    public RSSAdapter(ArrayList<RSSItemData> news_items, FragmentActivity ctx){
        this.news_items = news_items;
        this.ctx = ctx;
    }

    //** ViewHolder для новости с картинкой **//

    public  static  class ViewHolderWithImage extends RecyclerView.ViewHolder {
        CardView cv;
        TextView titleView;
        TextView dateView;
        ImageView thumbView;

        ViewHolderWithImage(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            titleView = (TextView) itemView.findViewById(R.id.postTitleLabel);
            dateView = (TextView) itemView.findViewById(R.id.postDateLabel);
            thumbView = (ImageView) itemView.findViewById(R.id.postThumb);
        }

    }

    //** ViewHolder для новости без картинки **//

    public  static  class ViewHolderWithoutImage extends RecyclerView.ViewHolder {
        CardView cv;
        TextView titleView;
        TextView dateView;

        ViewHolderWithoutImage(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv2);
            titleView = (TextView) itemView.findViewById(R.id.postTitleLabel2);
            dateView = (TextView) itemView.findViewById(R.id.postDateLabel2);
        }
    }

    //** Если новость с картинкой, то возвращаеся NEWS_WITH_IMAGE, в противном случае NEWS_WITHOUT_IMAGE **//

    @Override
    public int getItemViewType(int position) {

        if(news_items.get(position).postImage.equalsIgnoreCase("none")){
            return NEWS_WITHOUT_IMAGE;
        }
        return NEWS_WITH_IMAGE;
    }

    //** onCreateViewHolder **//

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        switch (viewType) {
            case NEWS_WITH_IMAGE:
                View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_item, viewGroup, false);
                ViewHolderWithImage rhv = new ViewHolderWithImage(v);
                return rhv;
            case NEWS_WITHOUT_IMAGE:
                View v2 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_item_without_image, viewGroup, false);
                ViewHolderWithoutImage rhv2 = new ViewHolderWithoutImage(v2);
                return rhv2;
        }
        return null;
    }

    //** onBindViewHolder **//

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder rssItemViewHolder, int i) {

        switch (rssItemViewHolder.getItemViewType()) {
            case NEWS_WITH_IMAGE:
                ViewHolderWithImage vhWithImage = (ViewHolderWithImage) rssItemViewHolder;
                vhWithImage.titleView.setText(news_items.get(i).postTitle);
                vhWithImage.dateView.setText(news_items.get(i).postDate);
                UrlImageViewHelper.setUrlDrawable(vhWithImage.thumbView, news_items.get(i).postImage);
                break;

            case NEWS_WITHOUT_IMAGE:
                ViewHolderWithoutImage vhWithoutImage  = (ViewHolderWithoutImage) rssItemViewHolder;
                vhWithoutImage.titleView.setText(news_items.get(i).postTitle);
                vhWithoutImage.dateView.setText(DateUtils.dateFromUNIXtoString(news_items.get(i).postDateUNIX));

                break;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent = new Intent(ctx, PostPage.class);
                intent.putExtra("post_id", news_items.get(position).postLink);
                intent.putExtra("date_unix", news_items.get(position).postDateUNIX);
                intent.putExtra("title", news_items.get(position).postTitle);
                intent.putExtra("post_content", news_items.get(position).postHTML);
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return news_items.size();
    }



}

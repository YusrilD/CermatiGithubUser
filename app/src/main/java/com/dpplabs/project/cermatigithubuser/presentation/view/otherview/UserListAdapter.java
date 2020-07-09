package com.dpplabs.project.cermatigithubuser.presentation.view.otherview;

import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.dpplabs.project.cermatigithubuser.R;
import com.dpplabs.project.cermatigithubuser.net.director.UserModel;
import com.dpplabs.project.cermatigithubuser.presentation.util.FooterViewHolder;
import com.dpplabs.project.cermatigithubuser.presentation.util.HeaderFooterAdapter;
import com.dpplabs.project.cermatigithubuser.presentation.util.HeaderViewHolder;
import com.dpplabs.project.cermatigithubuser.presentation.util.NormalViewHolder;
import com.dpplabs.project.cermatigithubuser.presentation.view.view.UserListView;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class UserListAdapter extends HeaderFooterAdapter<UserListAdapter.MyHeaderViewHolder, UserListAdapter.MyNormalViewHolder, UserListAdapter.MyFooterViewHolder> {

    private UserListView aView;

    private ProgressBar loading;
    private boolean showLoading = false;

    public UserListAdapter(UserListView aView) {
        this.aView=aView;
    }

    @Override
    public void onBindHeaderViewHolder(MyHeaderViewHolder viewHolder) {

    }

    @Override
    public void onBindNormalViewHolder(final MyNormalViewHolder viewHolder, int i) {
        final UserModel user = aView.getUserAt(i);
        viewHolder.name.setText(user.getLogin());
        Glide
                .with(aView.context)
                .load(user.getAvatar().toString())
                .apply(new RequestOptions()
                        .circleCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .placeholder(R.drawable.user_small))
                .transition(withCrossFade())
                .into(viewHolder.avatar);
        viewHolder.addToFavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setFavorite(!user.isFavorite());
                aView.onFavorIconClick(user);
                if(user.isFavorite()) viewHolder.addToFavor.setBackground(ContextCompat.getDrawable(aView.context, R.drawable.is_favorite));
                else viewHolder.addToFavor.setBackground(ContextCompat.getDrawable(aView.context, R.drawable.add_to_favorites));
            }
        });
        View.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aView.onUserClicked(user);
            }
        };
        viewHolder.click.setOnClickListener(onClick);
        if(user.isFavorite()) viewHolder.addToFavor.setBackground(ContextCompat.getDrawable(aView.context, R.drawable.is_favorite));
        else viewHolder.addToFavor.setBackground(ContextCompat.getDrawable(aView.context, R.drawable.add_to_favorites));
    }

    public void showLoading(){
        showLoading=true;
        if(loading!=null) loading.setVisibility(View.VISIBLE);
    }

    public void hideLoading(){
        showLoading=false;
        if(loading!=null) loading.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBindFooterViewHolder(MyFooterViewHolder viewHolder) {
        if(showLoading) loading.setVisibility(View.VISIBLE);
        else loading.setVisibility(View.INVISIBLE);
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_header, parent, false);
        return new MyHeaderViewHolder(v);
    }

    @Override
    public NormalViewHolder onCreateNormalViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user, parent, false);
        return new MyNormalViewHolder(v);
    }

    @Override
    public FooterViewHolder onCreateFooterViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_footer, parent, false);
        return new MyFooterViewHolder(v);
    }

    @Override
    public int getNormalItemsCount() {
        return aView.getUserCount();
    }

    class MyHeaderViewHolder extends HeaderViewHolder {
        MyHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    class MyFooterViewHolder extends FooterViewHolder {
        MyFooterViewHolder(View itemView) {
            super(itemView);
            loading = (ProgressBar) itemView.findViewById(R.id.progress_bar);
        }
    }

    class MyNormalViewHolder extends NormalViewHolder {
        private View click;
        private TextView name;
        private ImageView avatar;
        private View addToFavor;

        MyNormalViewHolder(View itemView) {
            super(itemView);
            click=itemView.findViewById(R.id.user_click);
            name = (TextView) itemView.findViewById(R.id.user_name);
            avatar = (ImageView) itemView.findViewById(R.id.user_avatar);
            addToFavor = itemView.findViewById(R.id.add_to_favorites);
        }
    }
}

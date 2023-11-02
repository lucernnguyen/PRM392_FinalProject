package com.example.grocery.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocery.R;
import com.example.grocery.databinding.ItemCommentBinding;
import com.example.grocery.models.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private Context context;
    private List<Comment> listComment=new ArrayList<>();

    public DetailViewModel detailViewModel;

    public CommentAdapter(Context context,DetailViewModel detailViewModel) {
        this.context = context;
        this.detailViewModel=detailViewModel;
    }


    public void setListComment(List<Comment> listComment) {
        this.listComment = listComment;
        notifyDataSetChanged();
    }

    public interface IComment{
        public void onCLick(int position);
    }
    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCommentBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_comment,parent,false);

        return new CommentAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        Comment comment=listComment.get(position);
        holder.binding.txtCusName.setText(detailViewModel.getCusNameById(comment.cID));
        holder.binding.txtContent.setText(comment.getContent());
        holder.binding.txtDate.setText(comment.getDate());

    }

    @Override
    public int getItemCount() {
        return listComment.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ItemCommentBinding binding;
        public ViewHolder(@NonNull ItemCommentBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}

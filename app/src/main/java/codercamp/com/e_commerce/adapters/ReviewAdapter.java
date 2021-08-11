package codercamp.com.e_commerce.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import codercamp.com.e_commerce.R;
import codercamp.com.e_commerce.models.ReviewModel;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.UserReview>{
    private List<ReviewModel> models;
    private Context context;

    public ReviewAdapter(List<ReviewModel> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @NonNull
    @Override
    public UserReview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.show_user_review,parent ,false);
        return new UserReview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserReview holder, int position) {
        ReviewModel reviewModel = models.get(position);

        holder.userName.setText(reviewModel.getUserName());
        holder.rating.setText(reviewModel.getRating());
        holder.review.setText(reviewModel.getReview());
        holder.date.setText(reviewModel.getCurrentDate());

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public  static class UserReview extends RecyclerView.ViewHolder {

        TextView userName,rating,review,date;

        public UserReview(@NonNull View itemView) {
            super(itemView);

            userName= itemView.findViewById(R.id.UserNameReview);
            rating= itemView.findViewById(R.id.ReviewRating);
            review= itemView.findViewById(R.id.UserReview);
            date= itemView.findViewById(R.id.UserReviewDate);

        }
    }
}

package msk.android.academy.javatemplate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import msk.android.academy.javatemplate.utils.StepsItem;

public class MetricsListAdapter extends RecyclerView.Adapter<MetricsListAdapter.ViewHolder> {
    @NonNull
    private List<StepsItem> metrics;
    private final LayoutInflater inflater;
//    @NonNull
//    private final newsItemClickListener clickListener;

    public MetricsListAdapter(@NonNull Context context,
                              @NonNull List<StepsItem> metrics) {
        this.metrics = metrics;
        this.inflater = LayoutInflater.from(context);
//        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                inflater.inflate(R.layout.cardview_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(metrics.get(position));
    }

    @Override
    public int getItemCount() {
        return metrics.size();
    }

//    public int getFirstItemId() {
//        return metrics.get(0).getId();
//    }

    public void replaceItems(@NonNull List<StepsItem> stepsItems) {
        metrics.clear();
        metrics.addAll(stepsItems);
        notifyDataSetChanged();
    }

    public void add(List<StepsItem> stepsItems) {
        this.metrics.addAll(stepsItems);
    }

//    public interface newsItemClickListener {
//        void onItemClick(StepsItemDTO item);
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView dateView;
        private TextView summView;
        private TextView goalView;
        private TextView int_walkView;
        private TextView int_aerobicView;
        private TextView int_runView;
        private ProgressBar progressBar;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
//            itemView.setOnClickListener(view -> {
//                int position = getAdapterPosition();
//                if (position != RecyclerView.NO_POSITION) {
//                    clickListener.onItemClick(news.get(position));
//                }
//            });
            dateView = (TextView) itemView.findViewById(R.id.date);
            summView = (TextView) itemView.findViewById(R.id.summ);
            goalView = (TextView) itemView.findViewById(R.id.goal);
            int_walkView = (TextView) itemView.findViewById(R.id.walk);
            int_aerobicView = (TextView) itemView.findViewById(R.id.aerobic);
            int_runView = (TextView) itemView.findViewById(R.id.run);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressbar);
        }

        void bind(StepsItem stepsItem) {
            summView.setText(String.valueOf(getSumm(stepsItem)));

            dateView.setText(stepsItem.getDate());
            String goalText = getSumm(stepsItem) + " / " + String.valueOf(stepsItem.getGoal());

            goalView.setText(goalText);
            int_walkView.setText(String.valueOf(stepsItem.getWalk()));
            int_aerobicView.setText(String.valueOf(stepsItem.getAerobic()));
            int_runView.setText(String.valueOf(stepsItem.getRun()));

            double i = ((stepsItem.getWalk() / (double) getSumm(stepsItem)) * 100);
            double j = ((stepsItem.getAerobic() / (double) getSumm(stepsItem)) * 100);

            if (i != 0 && i > 1) {
                progressBar.setProgress((int) i);
            }
            if (i != 0 && i < 1) progressBar.setProgress(1);

            if (j != 0 && j > 1) {
                progressBar.setSecondaryProgress((int) (i + j));
            }
            if (j != 0 && j < 1) progressBar.setSecondaryProgress((int) (i + 1));
        }

        private int getSumm(StepsItem stepsItem) {
            int summ = Integer.parseInt(String.valueOf(stepsItem.getWalk())) +
                    Integer.parseInt(String.valueOf(stepsItem.getAerobic())) + Integer.parseInt(String.valueOf(stepsItem.getRun()));
            return summ;
        }
    }
}

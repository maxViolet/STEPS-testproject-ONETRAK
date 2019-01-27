package msk.android.academy.Steps;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import msk.android.academy.Steps.utils.StepsItem;

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
        private RelativeLayout goalReachedBlock;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
//            itemView.setOnClickListener(view -> {
//                int position = getAdapterPosition();
//                if (position != RecyclerView.NO_POSITION) {
//                    clickListener.onItemClick(news.get(position));
//                }
//            });
            dateView = (TextView) itemView.findViewById(R.id.date);
            goalView = (TextView) itemView.findViewById(R.id.goal);
            int_walkView = (TextView) itemView.findViewById(R.id.walk);
            int_aerobicView = (TextView) itemView.findViewById(R.id.aerobic);
            int_runView = (TextView) itemView.findViewById(R.id.run);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressbar);
            goalReachedBlock = (RelativeLayout) itemView.findViewById(R.id.goal_reached_block);
        }

        void bind(StepsItem stepsItem) {
            dateView.setText(stepsItem.getDate());
            String goalText = getSumm(stepsItem) + " / " + String.valueOf(stepsItem.getGoal() + " steps");

            goalView.setText(goalText);
            int_walkView.setText(String.valueOf(stepsItem.getWalk()));
            int_aerobicView.setText(String.valueOf(stepsItem.getAerobic()));
            int_runView.setText(String.valueOf(stepsItem.getRun()));

            double i = ((stepsItem.getWalk() / (double) getSumm(stepsItem)) * 100);
            double j = ((stepsItem.getAerobic() / (double) getSumm(stepsItem)) * 100);
            double k = ((stepsItem.getRun() / (double) getSumm(stepsItem)) * 100);

            setProgressBar(i, j, k);

            //показывать достижение цели на день при выполнении
            if (getSumm(stepsItem) >= stepsItem.getGoal()) {
                goalReachedBlock.setVisibility(View.VISIBLE);
            }
        }

        private int getSumm(StepsItem stepsItem) {
            int summ = Integer.parseInt(String.valueOf(stepsItem.getWalk())) +
                    Integer.parseInt(String.valueOf(stepsItem.getAerobic())) + Integer.parseInt(String.valueOf(stepsItem.getRun()));
            return summ;
        }

        private void setProgressBar(double i, double j, double k) {
            progressBar.setScaleY(2f);
            if (i != 0 && i > 1) progressBar.setProgress((int) i);

            //минимальное отображение величины
            if (i != 0 && i < 1) progressBar.setProgress(1);

            if (j != 0 && j > 1) progressBar.setSecondaryProgress((int) (i + j));

            //минимальное отображение величины
            if (j != 0 && j < 1) progressBar.setSecondaryProgress((int) (i + 1));

//            if (k != 0) progressBar.tint;
        }
    }
}

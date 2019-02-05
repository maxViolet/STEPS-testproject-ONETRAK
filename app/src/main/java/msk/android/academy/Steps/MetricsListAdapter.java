package msk.android.academy.Steps;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Interpolator;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import msk.android.academy.Steps.utils.*;

public class MetricsListAdapter extends RecyclerView.Adapter<MetricsListAdapter.ViewHolder> {
    @NonNull
    private List<StepsItem> metrics;
    private final LayoutInflater inflater;

    public MetricsListAdapter(@NonNull Context context,
                              @NonNull List<StepsItem> metrics) {
        this.metrics = metrics;
        this.inflater = LayoutInflater.from(context);
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

    void replaceItems(@NonNull List<StepsItem> stepsItems) {
        metrics.clear();
        metrics.addAll(stepsItems);
        notifyDataSetChanged();
    }

    public void add(List<StepsItem> stepsItems) {
        this.metrics.addAll(stepsItems);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView dateView;
        private TextView goalView;
        private TextView int_walkView;
        private TextView int_aerobicView;
        private TextView int_runView;
        private RelativeLayout goalReachedBlock;
        private LinearLayout customProgressBarView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateView = itemView.findViewById(R.id.date);
            goalView = itemView.findViewById(R.id.goal);
            int_walkView = itemView.findViewById(R.id.walk);
            int_aerobicView = itemView.findViewById(R.id.aerobic);
            int_runView = itemView.findViewById(R.id.run);
            goalReachedBlock = itemView.findViewById(R.id.goal_reached_block);
            customProgressBarView = itemView.findViewById(R.id.progressBar);
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

            if (getSumm(stepsItem) >= stepsItem.getGoal()) {
                goalReachedBlock.setVisibility(View.VISIBLE);
            }
        }

        private int getSumm(StepsItem stepsItem) {
            return Integer.parseInt(String.valueOf(stepsItem.getWalk())) +
                    Integer.parseInt(String.valueOf(stepsItem.getAerobic())) + Integer.parseInt(String.valueOf(stepsItem.getRun()));
        }

        private void setProgressBar(double i, double j, double k) {
            CustomProgressBar customProgressBar = new CustomProgressBar(itemView.getContext());
            AnimatorSet animSet = new AnimatorSet();

            if (i != 0 && i > 1) {
                customProgressBar.setI((int) i);
            }
            //минимальное отображение величины
            if (i != 0 && i < 1) {
                customProgressBar.setI(2);
            }
            if (j != 0 && j > 1) {
                customProgressBar.setJ((int) j);
            }
            //минимальное отображение величины
            if (j != 0 && j < 1) {
                customProgressBar.setJ(2);
            }
            ObjectAnimator animI = ObjectAnimator.ofInt(customProgressBar, "i", 2, (int) i);
            animI.setDuration(1500);
            ObjectAnimator animJ = ObjectAnimator.ofInt(customProgressBar, "j", 2, (int) j);
            animJ.setDuration(1500);
            animSet.playTogether(animI, animJ);
            animSet.start();
            customProgressBarView.addView(customProgressBar);
        }
    }
}
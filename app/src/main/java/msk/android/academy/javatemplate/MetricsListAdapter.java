package msk.android.academy.javatemplate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import msk.android.academy.javatemplate.network.StepsItemDTO;

public class MetricsListAdapter extends RecyclerView.Adapter<MetricsListAdapter.ViewHolder> {
    @NonNull
    private List<StepsItemDTO> metrics;
    private final LayoutInflater inflater;
//    @NonNull
//    private final newsItemClickListener clickListener;
//    @NonNull
//    private final RequestManager imageLoader;

    public MetricsListAdapter(@NonNull Context context,
                              @NonNull List<StepsItemDTO> metrics) {
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

    @Override //указывает содержимое каждого элемента RecyclerView
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get our custom object from our dataset at this position
        holder.bind(metrics.get(position));
    }

    @Override
    public int getItemCount() {
        return metrics.size();
    }

//    public int getFirstItemId() {
//        return metrics.get(0).getId();
//    }

//    public void replaceItems(@NonNull List<StepsItemDTO> stepsItemDTOs) {
//        metrics.clear();
//        metrics.addAll(stepsItemDTOs);
//        notifyDataSetChanged();
//    }

    public void add(List<StepsItemDTO> stepsItemDTOs) {
        this.metrics.addAll(stepsItemDTOs);
    }

//    public interface newsItemClickListener {
//        void onItemClick(StepsItemDTO item);
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //        private int id;
        private TextView dateView;
        private TextView summView;
        private TextView goalView;
        private TextView int_walkView;
        private TextView int_aerobicView;
        private TextView int_runView;

//        public int returnID() {
//            return id;
//        }

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
        }

        void bind(StepsItemDTO stepsItemDTO) {
            dateView.setText(stepsItemDTO.getDate());
            summView.setText(getSumm(stepsItemDTO));
//            goalView.setText(stepsItemDTO.getGoal);

            int_walkView.setText(stepsItemDTO.getWalk());
            int_walkView.setText(stepsItemDTO.getWalk());
            int_walkView.setText(stepsItemDTO.getWalk());
        }

        private int getSumm(StepsItemDTO stepsItemDTO) {
            int summ = Integer.parseInt(String.valueOf(stepsItemDTO.getWalk())) +
                    Integer.parseInt(String.valueOf(stepsItemDTO.getAerobic())) + Integer.parseInt(String.valueOf(stepsItemDTO.getRun()));
            return summ;
        }
    }
}

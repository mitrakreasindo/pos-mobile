package com.mitrakreasindo.pos.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mitrakreasindo.pos.main.fragment.MainFragment;
import com.mitrakreasindo.pos.model.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisa on 19/06/17.
 */

public class MainQueueListAdapter  extends RecyclerView.Adapter<MainQueueListAdapter.ViewHolder>
{

  private List<Queue> queues = new ArrayList<>();
  private Context context;
  private LayoutInflater layoutInflater;
  int number = 0;

  public MainQueueListAdapter(Context context, List<Queue> queues)
  {
    this.queues = queues;
    this.context = context;
    this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }


  @Override
  public MainQueueListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
  {
    View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_queue_list, parent, false);

    return new ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(MainQueueListAdapter.ViewHolder holder, int position)
  {
    final Queue queue = queues.get(position);
    holder.txtNo.setText(String.valueOf(position+1));
    holder.txtName.setText(queue.getName());
    holder.txtQueueNumber.setText(queue.getQueueNumber());
    holder.txtValue.setText(queue.getValue());

  }

  @Override
  public int getItemCount()
  {
    return queues.size();
  }

  public void addQueue(List<Queue> queues)
  {
    this.queues.addAll(queues);
    notifyDataSetChanged();
  }

  public class ViewHolder extends RecyclerView.ViewHolder
  {
    private TextView txtNo ,txtName, txtQueueNumber, txtValue;
    public ViewHolder(View itemView)
    {
      super(itemView);
      txtNo = (TextView) itemView.findViewById(R.id.text_no);
      txtName = (TextView) itemView.findViewById(R.id.text_product);
      txtQueueNumber = (TextView) itemView.findViewById(R.id.text_no_queue);
      txtValue = (TextView) itemView.findViewById(R.id.text_value);
    }
  }
}

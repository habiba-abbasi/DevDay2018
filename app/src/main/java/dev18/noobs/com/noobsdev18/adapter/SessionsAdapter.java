package dev18.noobs.com.noobsdev18.adapter;

import android.support.v7.widget.RecyclerView;

public class SessionsAdapter {

//    LayoutInflater lf;
//    Context context;
//    ArrayList<String> data;
//
//    public MyAdapter(Context context,ArrayList<String> data){
//
//
//        this.context=context;
//        this.data=data;
//        lf=LayoutInflater.from(context);
//    }
//    //inflation will take place here
//    //this method will be called if new viewHolder rewuired otherwise recycling will do the job
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        System.out.println("hi");
//
//        View v=lf.inflate(R.layout.single_row,parent,false);
//        //sending inflatd view to ViewHolder so that it can find resource ids
//        //extra parameter of data myny apni asani ke liye dala he takay click p data le skun
//        MyViewHolder holder=new MyViewHolder(v,data);
//
//        //returning holder , holder now contains the ids of the layout
//        //values will be assigned to these ids in onBindViewHolder
//        return holder;
//    }
//
//    //Responsible to fill UI
//    //The holder parameter here is from onCreateHolder's return statement so it contains ids
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, int position) {
//
//
//        //also capitalizi
//        holder.name.setText(data.get(position).toString().substring(0,1).toUpperCase()+ data.get(position).toString().substring(1));
//
//    }
//
//    @Override
//    public int getItemCount() {
//        System.out.println(data.size());
//        return data.size();
//    }
//
//
//    class SessionsViewHolder extends RecyclerView.ViewHolder{
//
//        DatabaseReference ref;
//        TextView name;
//        ImageButton delete;
//        public  ArrayList<String> data;
//        //This will be called inside onCreateViewHolder
//        //parameter interView contains the inflated single row view
//        //here we will find resource ids
//        public MyViewHolder(View itemView,ArrayList<String> data) {
//            super(itemView);
//            name=(TextView) itemView.findViewById(R.id.textView);
//            delete=(ImageButton) itemView.findViewById(R.id.dustbin);
//            this.data=data;
//
//            delete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    System.out.println(MyViewHolder.this.data.get(getAdapterPosition()));
//
//                    ref= FirebaseDatabase.getInstance().getReference("tasks");
//                    ref.child(MyViewHolder.this.data.get(getAdapterPosition())).removeValue();
//                }
//            });
//        }
//    }
}
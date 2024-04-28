package com.example.assignment3;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
//    ContactClicked parentActivity;
//    public interface ContactClicked{
//        public void deleteContactFromList(int index);
//    }

    ArrayList<Contact> contacts;
    Context context;

    public ContactAdapter(Context c, ArrayList<Contact> list)
    {
        context = c;
        contacts = list;
        //parentActivity = (ContactClicked) c;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_contact, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(contacts.get(position).getName());
        holder.tvPhone.setText(contacts.get(position).getPhone());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(context);
                deleteDialog.setTitle("Confirmation");
                deleteDialog.setMessage("Do you really want to delete it?");
                deleteDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // delete code
                        MyDatabaseHelper database = new MyDatabaseHelper(context);
                        database.open();
                        database.deleteContact(contacts.get(holder.getAdapterPosition()).getId());
                        database.close();

                        contacts.remove(holder.getAdapterPosition());
                        notifyDataSetChanged();
                        //parentActivity.deleteContactFromList(holder.getAdapterPosition());
                    }
                });
                deleteDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                deleteDialog.show();

                return false;
            }
        });

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog editDialog = new AlertDialog.Builder(context).create();
                View view = LayoutInflater.from(context).inflate(R.layout.edit_contact_layout, null, false);
                editDialog.setView(view);

                EditText etName = view.findViewById(R.id.etName);
                EditText etPhone = view.findViewById(R.id.etPhone);
                Button btnUpdate = view.findViewById(R.id.btnUpdate);
                Button btnCancel = view.findViewById(R.id.btnCancel);

                etName.setText(contacts.get(holder.getAdapterPosition()).getName());
                etPhone.setText(contacts.get(holder.getAdapterPosition()).getPhone());

                editDialog.show();

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editDialog.dismiss();
                    }
                });

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = etName.getText().toString().trim();
                        String phone = etPhone.getText().toString();
                        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(context);
                        myDatabaseHelper.open();
                        myDatabaseHelper.updateContact(contacts.get(holder.getAdapterPosition()).getId(),
                                name, phone);
                        myDatabaseHelper.close();

                        editDialog.dismiss();

                        contacts.get(holder.getAdapterPosition()).setName(name);
                        contacts.get(holder.getAdapterPosition()).setPhone(phone);
                        notifyDataSetChanged();

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvName, tvPhone;
        ImageView ivEdit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            ivEdit = itemView.findViewById(R.id.ivEdit);
        }
    }
}

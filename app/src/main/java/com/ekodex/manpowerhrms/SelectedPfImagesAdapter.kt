package com.ekodex.manpowerhrms

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView


class SelectedPfImagesAdapter(var data: List<Uri>, var frag:String, var activity: FragmentActivity, private val onRemoveClick: (Uri) -> Unit) :
    Adapter<SelectedPfImagesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedPfImagesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.selected_image_item_view, parent, false)
        return SelectedPfImagesViewHolder(view)

    }

    override fun onBindViewHolder(holder: SelectedPfImagesViewHolder, position: Int) {
        val item = data[position]

        Glide.with(holder.img.context).load(item).into(holder.img)

        holder.removeImg.setOnClickListener {
            onRemoveClick(item)
        }

        if(frag == "EmployeeDetails")
        {
            holder.removeImg.visibility = View.GONE
        }

        holder.layout.setOnClickListener {
            if(frag == "EmployeeDetails")
            {
                //zoom image
            }
        }

        holder.img.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(it.context)

            val inflater = activity.layoutInflater
            val dialogView: View = inflater.inflate(R.layout.document_image_layout, null)
            dialogBuilder.setView(dialogView)

            var photoView: PhotoView = dialogView.findViewById(R.id.photo_view)

            // Use a library like Glide or Picasso to load the image
            Glide.with(holder.img.context).load(item).into(photoView)  // Load image using Glide or Picasso.into(photoView)

            val alertDialog = dialogBuilder.create()
            //alertDialog.setCancelable(false)
            //alertDialog.setCanceledOnTouchOutside(false)
            alertDialog.show()
        }

    }

    override fun getItemCount() = data.size

}

class SelectedPfImagesViewHolder(itemView: View) : ViewHolder(itemView) {
    val img: ImageView = itemView.findViewById(R.id.imageView48)
    val removeImg: ImageView = itemView.findViewById(R.id.imageView49)
    val layout:ConstraintLayout = itemView.findViewById(R.id.doc_img)
}

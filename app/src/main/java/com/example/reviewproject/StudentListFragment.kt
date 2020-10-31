package com.example.reviewproject

import android.app.ProgressDialog.show
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

private const val TAG = "CrimeListFragment"


class StudentListFragment :Fragment() ,InputDialogFragment.Callbacks{

    private lateinit var studentRecyclerView: RecyclerView
    private lateinit var Empty_List: TextView
    private lateinit var AddStudent: Button

    private var adapter: StudentAdapter? = null

    private val studentListViewModel: StudentListViewModel by lazy {
        ViewModelProviders.of(this).get(StudentListViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        //Log.d(TAG, "Total Of Students: ${studentListViewModel.students.size}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_student_list_item, container, false)
        studentRecyclerView = view.findViewById(R.id.stu_recycler_view) as RecyclerView
        Empty_List = view.findViewById(R.id.list_empty) as TextView
        AddStudent = view.findViewById(R.id.new_student) as Button

        studentRecyclerView.layoutManager = LinearLayoutManager(context)
        updateUI()
        return view
    }
    private inner class StudentHolder(view: View)
        : RecyclerView.ViewHolder(view) , View.OnClickListener{

        private lateinit var student: Students
        val StudentNameView: TextView = itemView.findViewById(R.id.student_name)
        val StudentNumView: TextView = itemView.findViewById(R.id.student_num)
        val StudentPassView : TextView = itemView.findViewById(R.id.student_pass)
        val ImageDelete : ImageButton = itemView.findViewById(R.id.button_delete)
        init {
            itemView.setOnClickListener(this)
        }
        init {
            ImageDelete.setOnClickListener {
                onStudentDeleted(adapterPosition)
            }
        }



        fun bind(student:Students) {
            this.student = student
            StudentNameView.text = this.student.Stu_Name
            StudentNumView.text=this.student.Num.toString()
            StudentPassView.text=this.student.pass.toString()

        }

        override fun onClick(p0: View?) {
            Toast.makeText(context, "${student.Stu_Name}!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private inner class StudentAdapter(var students: List<Students>)
        : RecyclerView.Adapter<StudentHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentHolder {
            val view = layoutInflater.inflate(R.layout.list_item_student, parent, false)
            return StudentHolder(view)

        }


        override fun getItemCount(): Int {
            return students.size
            if (students.isNotEmpty()) {
                Empty_List.visibility = View.GONE
                AddStudent.visibility = View.GONE
            } else {

                Empty_List.visibility = View.VISIBLE
                AddStudent.visibility = View.VISIBLE
            }
        }


        override fun onBindViewHolder(holder: StudentHolder, position: Int) {
            val student = students[position]
            holder.apply {
                holder.bind(student)

            }

        }

    }
    private fun updateUI() {
        val students = studentListViewModel.students
        adapter = StudentAdapter(students)
        studentRecyclerView.adapter = adapter




    }
    companion object {
        fun newInstance(): StudentListFragment {
            return StudentListFragment()

        }

    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_student_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_student -> {
               val student = Students()
             //   studentListViewModel.addStudent(Students(UUID.randomUUID(),"wafaa",9,true))
             //   Toast.makeText(context, " add student", Toast.LENGTH_SHORT) .show()
             //    updateUI()
                InputDialogFragment().apply{
                    setTargetFragment(this@StudentListFragment,0)
                    show(this@StudentListFragment.requireFragmentManager(),"Input")
                }
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }}

    override fun onStudentAdded(student: Students) {
        studentListViewModel.addStudent(student)
        updateUI()
    }
    override fun onStudentDeleted(item : Int){
        studentListViewModel.deleteStudent(item)
        updateUI()
    }
}
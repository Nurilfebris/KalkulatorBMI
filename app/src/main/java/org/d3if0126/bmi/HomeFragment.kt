package org.d3if0126.bmi


import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_home.view.tv_bb
import org.d3if0126.bmi.databinding.FragmentHomeBinding

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {
    private var beratb = 0;
    private var tinggib=0;
    private var count=0;
    private var bkategori=0;
    override fun onCreateView(


        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding=DataBindingUtil.inflate<FragmentHomeBinding>(inflater,R.layout.fragment_home,container,false)

        binding.btnHitung.setOnClickListener {
            if (rb_pria.isChecked||rb_wanita.isChecked !=null){

                if (rb_pria.isChecked){
                    beratb = pt_bb.text.toString().toInt()
                    tinggib = pt_tb.text.toString().toInt()
                    val tinggiM=tinggib/100.toDouble()
                    count =(beratb/tinggib*tinggib)
                    binding.tvHasil.setText(count)

                    if (count < 20.5){
                        tv_kategori.setText("Kurus")
                    }else if (count >=27.00)
                        tv_kategori.setText("gemuk")
                    else{
                        tv_kategori.setText("ideal")
                    }
                }


            }
        }


        binding.btnBagikan.setOnClickListener {
            onShare()
        }
        binding.btnSaran.setOnClickListener {
            view:View->view.findNavController().navigate(R.id.action_homeFragment_to_gemukFragment)
        }
        if (savedInstanceState != null) {
            // Get all the game state information from the bundle, set it
            beratb = savedInstanceState.getInt("KEY_BERAT", 0)
            tinggib = savedInstanceState.getInt("KEY_TINGGI", 0)
            count = savedInstanceState.getInt("KEY_NILAI", 0)
            bkategori = savedInstanceState.getInt("KEY_KATEGORI", 0)


        }

        setHasOptionsMenu(true)
        return binding.root
    }
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!,
            view!!.findNavController())
                || super.onOptionsItemSelected(item)
    }
    private fun onShare() {
        val shareIntent = ShareCompat.IntentBuilder.from(activity!!)
            .addEmailTo(arrayOf("mobro.d3if@gmail.com"))
            .setSubject("Berat saya"+tv_kategori.text.toString())
            .setText("Berat badan : "+beratb.toString()+" kg"+
                    "\n Tinggi badan : "+tinggib.toString()+"cm"+
                    "\n Jenis Kelamin : "+
                    "\n nilai BMI :"+tv_hasil.text.toString()+
                    "\n kategori :"+tv_kategori.text.toString())
            .setType("text/plain")
            .intent
        try {
            startActivity(shareIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(context,"gagal",
                Toast.LENGTH_LONG).show()
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("KEY_BERAT", beratb)
        outState.putInt("KEY_TINGGI", tinggib)
        outState.putInt("KEY_NILAI", count)
        outState.putInt("KEY_KATEGORI", bkategori)

        super.onSaveInstanceState(outState)
    }


}

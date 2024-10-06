package frgp.utn.edu.com.tabcontrol.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import frgp.utn.edu.com.tabcontrol.clases.AltaFragment;
import frgp.utn.edu.com.tabcontrol.clases.ListadoFragment;
import frgp.utn.edu.com.tabcontrol.clases.ModificarFragment;

public class ViewPageAdapter extends FragmentStateAdapter {


    public ViewPageAdapter(FragmentActivity fa) {
        super(fa);
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new AltaFragment();
            case 1:
                return new ModificarFragment();
            case 2:
                return new ListadoFragment();
            default:
                return new AltaFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}


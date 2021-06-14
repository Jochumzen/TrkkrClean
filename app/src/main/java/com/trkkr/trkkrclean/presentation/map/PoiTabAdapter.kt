import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.minipoibottomsheets.poi_overview
import com.example.minipoibottomsheets.poi_photos
import com.example.minipoibottomsheets.poi_wiki

class PoiTabAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 3;
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> {
                poi_overview()
            }
            1 -> {
                poi_wiki()
            }
            2 -> {
                poi_photos()
            }
            else -> {
                poi_overview()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return "Overview"
            }
            1 -> {
                return "Wikipedia"
            }
            2 -> {
                return "Feed"
            }
        }
        return super.getPageTitle(position)
    }

}
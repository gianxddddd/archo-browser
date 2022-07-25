package ga.gianxd.browser.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ga.gianxd.browser.MainActivity
import ga.gianxd.browser.databinding.FragmentBrowserBinding

class BrowserFragment : Fragment() {
    private var _binding: FragmentBrowserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBrowserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.settingsButton.setOnClickListener {
            (requireActivity() as MainActivity).switch<PreferencesFragment>()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        (requireActivity() as MainActivity).supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as MainActivity).supportActionBar!!.show()
    }
}
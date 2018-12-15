package ie.ul.daveberry.budget.view;


import android.support.v4.app.Fragment;

public interface NavigationDrawerItemView {
  void render(Fragment fragment);

  void goToHome();
}

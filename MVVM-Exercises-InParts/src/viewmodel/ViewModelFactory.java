package viewmodel;

import model.Model;

public class ViewModelFactory
{
  private ManageExerciseViewModel mevm;
  private ListExerciseViewModel levm;
  private ViewState state;

  public ViewModelFactory(Model model) {
    state = new ViewState();
    mevm = new ManageExerciseViewModel(model, state);
    levm = new ListExerciseViewModel(model, state);
  }

  public ManageExerciseViewModel getManageExerciseViewModel() {
    return mevm;
  }

  public ListExerciseViewModel getListExerciseViewModel() {
    return levm;
  }
}

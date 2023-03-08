package viewmodel;

import javafx.beans.property.*;
import model.Exercise;
import model.Model;

public class ManageExerciseViewModel
{
  private StringProperty errorProperty;
  private StringProperty headerProperty;
  private ObjectProperty<Boolean> completedProperty;
  private StringProperty topicProperty;
  private IntegerProperty numberProperty;
  private IntegerProperty sessionProperty;
  private ObjectProperty<Boolean> editableProperty;
  private Model model;
  private ViewState viewState;

  public ManageExerciseViewModel(Model model, ViewState viewState)
  {
    this.model = model;
    this.viewState = viewState;

    errorProperty = new SimpleStringProperty();
    headerProperty = new SimpleStringProperty();
    completedProperty = new SimpleObjectProperty<>();
    topicProperty = new SimpleStringProperty();
    numberProperty = new SimpleIntegerProperty();
    sessionProperty = new SimpleIntegerProperty();
    editableProperty = new SimpleObjectProperty<>();
  }

  //used to refresh the screen when switching to this screen. requires data from viewState,
  // since that is stored from the previous controller
  public void reset() {
    Exercise exercise = model.getExercise(viewState.getNumber());

    if (exercise == null)
    {
      errorProperty.set("");
      completedProperty.set(false);
      topicProperty.set("");
      numberProperty.set(0);
      sessionProperty.set(0);
      editableProperty.set(true);
      System.out.println("ManageModel reset with null selection");
    }

    else {
      errorProperty.set("");
      completedProperty.set(exercise.isCompleted());
      topicProperty.set(exercise.getTopic());
      numberProperty.set(exercise.getExerciseNumber());
      sessionProperty.set(exercise.getSessionNumber());
      editableProperty.set(!viewState.isRemove()); //false if going to be removed
      System.out.println("ManageModel reset with non-null selection: " + exercise.getNumber());

    }

    //set header depending on context
    if (viewState.isRemove()) //if set for removal
      headerProperty.set("Remove exercise");
    else if (viewState.getNumber() == null) //if nothing selected
      headerProperty.set("Add exercise");
    else
      headerProperty.set("Edit exercise");
  }

  public StringProperty getErrorProperty() { return errorProperty; }

  public StringProperty getHeaderProperty() { return headerProperty; }

  public ObjectProperty<Boolean> getCompletedProperty() { return completedProperty; }

  public StringProperty getTopicProperty() { return topicProperty; }

  public IntegerProperty getNumberProperty() { return numberProperty; }

  public IntegerProperty getSessionProperty() { return sessionProperty; }

  public ObjectProperty<Boolean> getEditableProperty() { return editableProperty; }

  private Exercise createExerciseObject() {
    return new Exercise(sessionProperty.get(), numberProperty.get(),
        topicProperty.get());
  }

  public boolean accept() {
    try {
      createExerciseObject(); //if all fields not filled, won't work
    }
    catch (Exception e)
    {
      errorProperty.set(e.getMessage());
      e.printStackTrace();
      return false;
    }
    return true;
  }

}

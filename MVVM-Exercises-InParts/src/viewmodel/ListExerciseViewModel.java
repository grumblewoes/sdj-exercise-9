package viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Exercise;
import model.Model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ListExerciseViewModel implements PropertyChangeListener
{
  private ObservableList<SimpleExerciseViewModel> list;
  private ObjectProperty<SimpleExerciseViewModel> selectedExerciseProperty;
  private StringProperty errorProperty;
  private Model model;
  private ViewState viewState;

  public ListExerciseViewModel(Model model, ViewState viewState)
  {
    this.model = model;
    this.viewState = viewState;

    list = FXCollections.observableArrayList();
    selectedExerciseProperty = new SimpleObjectProperty<>();
    errorProperty = new SimpleStringProperty();
    loadFromModel();

    model.addListener(this);
  }

  public void clear() {
    viewState.removeNumber();
    errorProperty.set("");
  }

  public void loadFromModel() {
    //clears old values
    list.clear();
    for (Exercise e : model.getAllExercises())
    {
      addSimpleExercise(e);
    }
  }

  public void addEdit() {
    loadFromModel();
  }

  public boolean remove() {
    //if set to remove
    SimpleExerciseViewModel selected = selectedExerciseProperty.get();
    if (selected != null)
    {
      viewState.setNumber(selected.getNumberProperty().get());
      viewState.setRemove(true);
      return true;
    }
    else
    {
      errorProperty.set("Nothing is selected.");
      return false;
    }
  }

  public ObservableList<SimpleExerciseViewModel> getAll() {
    return list;
  }

  public void setSelected(SimpleExerciseViewModel exerciseVm) {
    selectedExerciseProperty.set(exerciseVm);
    viewState.setNumber(exerciseVm.getNumberProperty().get());
  }

  public StringProperty getErrorProperty(){
    return errorProperty;
  }

  private void removeSimpleExercise(String number) {

    model.removeExercise(number);
  }
  private void addSimpleExercise(Exercise exercise) {
    list.add(new SimpleExerciseViewModel(exercise));
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    //error checking for add and remove
    System.out.println(evt.getPropertyName());
    if (evt.getPropertyName().equals("Add"))
      addSimpleExercise((Exercise) evt.getNewValue());
    else if (evt.getPropertyName().equals("Remove"))
      removeSimpleExercise(evt.getNewValue().toString());
    else if (evt.getPropertyName().equals("Edit"))
      addEdit();
  }
}

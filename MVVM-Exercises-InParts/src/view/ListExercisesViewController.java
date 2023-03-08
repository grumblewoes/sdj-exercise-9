package view;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import viewmodel.ListExerciseViewModel;
import viewmodel.SimpleExerciseViewModel;

public class ListExercisesViewController
{
  @FXML private Label errorLabel;

  @FXML private TableView<SimpleExerciseViewModel> exercisesTable;
  @FXML private TableColumn<SimpleExerciseViewModel, String> numberColumn;
  @FXML private TableColumn<SimpleExerciseViewModel, String> topicColumn;
  @FXML private TableColumn<SimpleExerciseViewModel, Boolean> completedColumn;

  private Region root;

  private ViewHandler viewHandler;

  private ListExerciseViewModel viewModel;

  @FXML void addEditButton()
  {
    SimpleExerciseViewModel exercise = exercisesTable.getSelectionModel().getSelectedItem();

    if (exercise != null)
      viewModel.setSelected(exercise);

    viewHandler.openView("manage");
  }

  @FXML void removeButton()
  {
    SimpleExerciseViewModel exercise = exercisesTable.getSelectionModel().getSelectedItem();
    if (exercise != null)
    {
      viewModel.setSelected(exercise);
      viewHandler.openView("manage");
    }

    viewModel.remove();
  }

  public void init(ViewHandler viewHandler, ListExerciseViewModel viewModel,
      Region root)
  {
    this.viewHandler = viewHandler;
    this.viewModel = viewModel;
    this.root = root;

    //bind all item properties
    errorLabel.textProperty().bind(viewModel.getErrorProperty());
    numberColumn.setCellValueFactory(
        cellData -> cellData.getValue().getNumberProperty());
    topicColumn.setCellValueFactory(
        cellData -> cellData.getValue().getTopicProperty());
    completedColumn.setCellValueFactory(
        cellData -> cellData.getValue().getCompletedProperty());
    exercisesTable.setItems(viewModel.getAll());

    reset();
  }

  public void reset()
  {
    viewModel.clear();
    exercisesTable.getSelectionModel().clearSelection();
  }

  public Region getRoot()
  {
    return root;
  }

}
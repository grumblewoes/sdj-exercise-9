package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import utility.IntStringConverter;
import viewmodel.ManageExerciseViewModel;

public class ManageExerciseViewController {

  @FXML
  private RadioButton completedRadiobutton;

  @FXML
  private Label errorLabel;

  @FXML
  private Label headerLabel;

  @FXML
  private TextField numberField;

  @FXML
  private TextField sessionField;

  @FXML
  private Button submitButton;

  @FXML
  private TextField topicField;

  private Region root;

  private ViewHandler viewHandler;

  private ManageExerciseViewModel viewModel;

  private IntStringConverter converter;


  @FXML
  void cancelButton(ActionEvent event) {
    viewHandler.openView("list");

    reset();
  }

  @FXML
  void onEnter(ActionEvent event) {
    if (sessionField.isFocused())
      numberField.requestFocus();
    else if (numberField.isFocused())
      topicField.requestFocus();
    else if (topicField.isFocused())
      submitButton.requestFocus();
  }

  @FXML
  void submitButton(ActionEvent event) {
    if (viewModel.accept())
      viewHandler.openView("list");
    reset();
  }

  public void init(ViewHandler viewHandler, ManageExerciseViewModel viewModel, Region root) {
    this.viewHandler = viewHandler;
    this.viewModel = viewModel;
    this.root = root;

    converter = new IntStringConverter();

    //bind all item properties
    numberField.textProperty().bindBidirectional(viewModel.getNumberProperty(), converter);
    topicField.textProperty().bindBidirectional(viewModel.getTopicProperty());
    sessionField.textProperty().bindBidirectional(viewModel.getSessionProperty(), converter);
    headerLabel.textProperty().bind(viewModel.getHeaderProperty());
    errorLabel.textProperty().bind(viewModel.getErrorProperty());
    completedRadiobutton.selectedProperty().bindBidirectional(viewModel.getCompletedProperty());

    reset();

  }

  public void reset() {
    viewModel.reset();
    if (!viewModel.getEditableProperty().get())
    {
      numberField.setEditable(false);
      topicField.setEditable(false);
      sessionField.setEditable(false);
    }
    else {
      numberField.setEditable(true);
      topicField.setEditable(true);
      sessionField.setEditable(true);
    }
  }

  public Region getRoot() {
    return root;
  }

}

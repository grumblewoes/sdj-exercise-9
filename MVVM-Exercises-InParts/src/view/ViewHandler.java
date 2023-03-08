package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import viewmodel.ViewModelFactory;

public class ViewHandler
{
  private Stage primaryStage;
  private Scene currentScene;
  private ViewModelFactory viewModelFactory;

  private ListExercisesViewController listViewController;

  private ManageExerciseViewController manageViewController;

  public ViewHandler(ViewModelFactory viewModelFactory) {
      this.viewModelFactory = viewModelFactory;
      currentScene = new Scene(new Region());
  }


  public void start(Stage primaryStage)
  {
    this.primaryStage = primaryStage;
    openView("list");
  }

  public void openView(String id)
  {
    Region root = null;
    switch (id)
    {
      case "list":
        root = loadListView("ListExercisesView.fxml");
        break;
      case "manage":
        root = loadManageView("ManageExerciseView.fxml");
        break;
    }
    currentScene.setRoot(root);
    String title = "";
    /*if (root.getUserData() != null)
    {
      title += root.getUserData();
    }*/
    primaryStage.setTitle(title);
    primaryStage.setScene(currentScene);
    primaryStage.show();
  }

  private Region loadListView(String fxmlFile) {
    if (listViewController == null)
    {
      try
      {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        listViewController = loader.getController();
        listViewController
            .init(this, viewModelFactory.getListExerciseViewModel(), root);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      listViewController.reset();
    }
    return listViewController.getRoot();
  }

  private Region loadManageView(String fxmlFile) {
    if (manageViewController == null)
    {
      try
      {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        manageViewController = loader.getController();
        manageViewController
            .init(this, viewModelFactory.getManageExerciseViewModel(), root);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      manageViewController.reset();
    }
    return manageViewController.getRoot();
  }
}

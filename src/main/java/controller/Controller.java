package controller;

import javafx.scene.Parent;
import model.ScreenManager;

import java.io.IOException;

public abstract class Controller {
    protected SideBarController sideBarController;
    protected HomeController homeController;
    protected BookMarkController bookMarkController;
    protected SearchController searchController;
    protected SearchTextController searchTextController;
    protected SearchWordController searchWordController;
    protected FlashCardController flashCardController;
    protected AddWordController addWordController;
    protected LogInController logInController;
    protected SignUpController signUpController;
    protected CrosswordGameController crosswordGameController;
    protected HangmanGameController hangmanGameController;
    protected HelpController helpController;
    protected ShowWordController showWordController;
    public void init() {
        sideBarController = (SideBarController) ScreenManager.getInstance().getController("SideBar");
        homeController = (HomeController) ScreenManager.getInstance().getController("Home");
        bookMarkController = (BookMarkController) ScreenManager.getInstance().getController("BookMark");
        searchController = (SearchController) ScreenManager.getInstance().getController("Search");
        searchTextController = (SearchTextController) ScreenManager.getInstance().getController("SearchText");
        searchWordController = (SearchWordController) ScreenManager.getInstance().getController("SearchWord");
        flashCardController = (FlashCardController) ScreenManager.getInstance().getController("FlashCard");
        addWordController = (AddWordController) ScreenManager.getInstance().getController("AddWord");
        logInController = (LogInController) ScreenManager.getInstance().getController("LogIn");
        signUpController = (SignUpController) ScreenManager.getInstance().getController("SignUp");
        crosswordGameController = (CrosswordGameController) ScreenManager.getInstance().getController("CrosswordGame");
        hangmanGameController = (HangmanGameController) ScreenManager.getInstance().getController("HangmanGame");
        helpController = (HelpController) ScreenManager.getInstance().getController("Help");
        showWordController = (ShowWordController) ScreenManager.getInstance().getController("ShowWord");
    }
}

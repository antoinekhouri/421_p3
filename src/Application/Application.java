package Application;

import view.MainPage;

public class Application {
	public static void main(String[] args) {

	      // start UI
	      java.awt.EventQueue.invokeLater(new Runnable() {
	          public void run() {
	              new MainPage().setVisible(true);
	          }
	      });
		}
}

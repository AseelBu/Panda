package Utils;

import Model.Question;
import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class QuestionListCell extends ListCell<Question> {
	
	
	
	@Override
    protected void updateItem(Question item, boolean empty) {
        super.updateItem(item, empty);
        if(item == null) {
        	return;
        }
        setText(item.getContent());
        this.setFont(new Font("verdana", 16));
        if(item.getDifficulty().equals(DifficultyLevel.EASY)) {
        	setTextFill(Color.GREEN);

        }
        else if(item.getDifficulty().equals(DifficultyLevel.MEDIOCRE)) {
        	setTextFill(Color.ORANGE);
        	
        }
        else if(item.getDifficulty().equals(DifficultyLevel.HARD)) {
        	setTextFill(Color.RED);
        	
        }
        
    }

}

package ru.eleron.osa.lris.schedule.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.utils.frame.FrameControllerBaseIF;
import ru.eleron.osa.lris.schedule.utils.storage.ConstantsSittings;

@Component
public class ChoosePlanForDayController implements FrameControllerBaseIF{

    private final static Logger logger = LogManager.getLogger(ChoosePlanForDayController.class);

    @FXML
    private Spinner<Integer> spinnerStartWork;

    public void initialize()
    {
        System.out.println("URS");
        logger.info("Controller " + getClass().getSimpleName() + " loaded");
    }

    @Override
    public void initData() {
        logger.info("initData in " + this.getClass().getSimpleName() + " loaded");
    }

    @Override
    public void configureElements() {
        /*spinnerStartWork.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
        final SpinnerValueFactory<Double> spinnerValueFactory = new SpinnerValueFactory.ListSpinnerValueFactory(
                                                                        FXCollections.observableArrayList(
                                                                                ConstantsSittings.APPLICATION_INITIAL_TASK_TIME.getDoubleListConstant()
                                                                        )
                                                                );
        spinnerValueFactory.setValue(ConstantsSittings.APPLICATION_INITIAL_TASK_TIME_DEFAULT.getDoubleContant());
        spinnerStartWork.setValueFactory(spinnerValueFactory);*/
        spinnerStartWork.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100,1));
        logger.info("configureElements in " + this.getClass().getSimpleName() + " done");
    }

    @Override
    public void enableTooltips() {

    }
}

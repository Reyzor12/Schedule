package ru.eleron.osa.lris.schedule.utils.uielements;

import javafx.scene.image.Image;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.eleron.osa.lris.schedule.utils.storage.ConstantsForElements;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Reyzor
 * @version 1.0
 * @since 09.05.2018
 */

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SpinnerForSchedule implements SpinnerForScheduleIF<Image> {

    public static final Map<Integer, Image> NUMBER_HASH = new LinkedHashMap<>();
    private Double defaultValue;
    private Double currentValue;
    private List<Double> items;

    public SpinnerForSchedule(Double defaultValue, Collection<Double> items)
    {
        this.items = new ArrayList<>(items);
        if (NUMBER_HASH.isEmpty()) {
            loadDataToMap();
        }
        if (!this.items.isEmpty() && this.items.contains(defaultValue))
        {
            this.defaultValue = defaultValue;
        } else {
            this.defaultValue = 0d;
        }
        this.currentValue = this.defaultValue;
    }

    @Override
    public List<Image> next()
    {
        if (hasNext())
        {
            currentValue = addHalfHour(currentValue);
        }
        return fromDoubleToImage(currentValue);
    }

    @Override
    public List<Image> previous()
    {
        if (hasPrevious())
        {
            currentValue = removeHalfHour(currentValue);
        }
        return fromDoubleToImage(currentValue);
    }

    @Override
    public boolean hasNext()
    {
        return items.contains(addHalfHour(currentValue));
    }

    @Override
    public boolean hasPrevious()
    {
        return items.contains(removeHalfHour(currentValue));
    }

    @Override
    public List<Image> getCurrentValue()
    {
        return fromDoubleToImage(currentValue);
    }

    /**
     * convert double to list of image
     * 1) parse double to list of integer
     * 2) for each integer get image
     * 3) collect image to list in right order
     * */

    private List<Image> fromDoubleToImage(Double value)
    {
        String[] characters = value.toString().replace(".","").split("");
        return Arrays.asList(characters).stream().map(character -> NUMBER_HASH.get(Integer.parseInt(character))).collect(Collectors.toList());
    }

    /**
     * add to double 0.3 and if double rest 0.6 when double = double + 1 - 0.6
     * */

    private Double addHalfHour(Double time)
    {
        if (time - time.intValue() == 0)
        {
            return time + 0.3d;
        } else {
            return time + 0.7d;
        }
    }

    /**
     * substract from double 0.3 and if double rest 0.6 when double = double + 1 - 0.6
     * */

    private Double removeHalfHour(Double time)
    {
        if (time - time.intValue() == 0)
        {
            return time - 0.7d;
        } else {
            return time - 0.3d;
        }
    }

    private void loadDataToMap()
    {
        NUMBER_HASH.putIfAbsent(0, new Image(getClass().getClassLoader().getResource(ConstantsForElements.NUMBER_0.getMessage()).toString()));
        NUMBER_HASH.putIfAbsent(1, new Image(getClass().getClassLoader().getResource(ConstantsForElements.NUMBER_1.getMessage()).toString()));
        NUMBER_HASH.putIfAbsent(2, new Image(getClass().getClassLoader().getResource(ConstantsForElements.NUMBER_2.getMessage()).toString()));
        NUMBER_HASH.putIfAbsent(3, new Image(getClass().getClassLoader().getResource(ConstantsForElements.NUMBER_3.getMessage()).toString()));
        NUMBER_HASH.putIfAbsent(4, new Image(getClass().getClassLoader().getResource(ConstantsForElements.NUMBER_4.getMessage()).toString()));
        NUMBER_HASH.putIfAbsent(5, new Image(getClass().getClassLoader().getResource(ConstantsForElements.NUMBER_5.getMessage()).toString()));
        NUMBER_HASH.putIfAbsent(6, new Image(getClass().getClassLoader().getResource(ConstantsForElements.NUMBER_6.getMessage()).toString()));
        NUMBER_HASH.putIfAbsent(7, new Image(getClass().getClassLoader().getResource(ConstantsForElements.NUMBER_7.getMessage()).toString()));
        NUMBER_HASH.putIfAbsent(8, new Image(getClass().getClassLoader().getResource(ConstantsForElements.NUMBER_8.getMessage()).toString()));
        NUMBER_HASH.putIfAbsent(9, new Image(getClass().getClassLoader().getResource(ConstantsForElements.NUMBER_9.getMessage()).toString()));
    }
}

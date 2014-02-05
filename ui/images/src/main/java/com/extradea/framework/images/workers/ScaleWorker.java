package com.extradea.framework.images.workers;

import android.graphics.Bitmap;
import com.extradea.framework.images.ImageController;
import com.extradea.framework.images.tasks.ImageTask;
import com.extradea.framework.images.tasks.ScaleTask;
import com.extradea.framework.images.utils.ImageUtils;

import java.util.concurrent.Callable;

/**
 * Created by ex3ndr on 17.01.14.
 */
public class ScaleWorker implements ImageWorker {

    @Override
    public boolean acceptTask(ImageTask task, ImageController controller) {
        return task instanceof ScaleTask;
    }

    @Override
    public int processTask(ImageTask task, ImageController controller) {
        final ScaleTask scaleTask = (ScaleTask) task;
        final Bitmap src = task.getRequiredTasks()[0].getResult();
        Bitmap scaled = controller.getBitmapDecoder().executeGuarded(new Callable<Bitmap>() {
            @Override
            public Bitmap call() throws Exception {
                return ImageUtils.scale(src, scaleTask.getW(), scaleTask.getH());
            }
        });
        task.setResult(scaled);
        return RESULT_OK;
    }

    @Override
    public boolean isPausable() {
        return true;
    }
}

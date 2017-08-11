package com.mitrakreasindo.pos.common.job;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.mitrakreasindo.pos.common.TableHelper.TableCategoryHelper;
import com.mitrakreasindo.pos.main.R;
import com.mitrakreasindo.pos.main.stock.category.CategoryFormActivity;
import com.mitrakreasindo.pos.model.Category;

import java.util.HashMap;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lisa on 10/08/17.
 */

public class PostCategoryJob extends Job
{

  private Category category;

  protected PostCategoryJob(Params params)
  {
    super(params);
  }

  @Override
  public void onAdded()
  {

  }

  @Override
  public void onRun() throws Throwable
  {


  }

  @Override
  protected void onCancel(int cancelReason, @Nullable Throwable throwable)
  {

  }

  @Override
  protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount)
  {
    return null;
  }
}

package com.leory.badminton.mine.mvp.model;

import com.leory.badminton.mine.mvp.contract.MineContract;
import com.leory.commonlib.di.scope.ActivityScope;
import com.leory.commonlib.http.IRepositoryManager;
import com.leory.commonlib.mvp.BaseModel;

import javax.inject.Inject;

/**
 * Describe : mine model
 * Author : leory
 * Date : 2019-06-19
 */
@ActivityScope
public class MineModel extends BaseModel implements MineContract.Model {
    @Inject
    public MineModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }
}

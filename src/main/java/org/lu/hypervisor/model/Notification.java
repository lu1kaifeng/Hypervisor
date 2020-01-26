package org.lu.hypervisor.model;

import org.lu.hypervisor.entity.Misbehavior;

public class Notification {
    private Misbehavior misbehavior;

    public Misbehavior getMisbehavior() {
        return misbehavior;
    }

    public void setMisbehavior(Misbehavior misbehavior) {
        this.misbehavior = misbehavior;
    }
}

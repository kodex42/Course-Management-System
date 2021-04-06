package com.comp3000.project.cms.BLL.updaters;

public interface Updater<Entity, Update> {
    Entity update(Entity entity, Update update) throws Exception;
}

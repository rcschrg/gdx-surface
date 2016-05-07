package de.verygame.square.core.event;

import com.badlogic.gdx.Gdx;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.verygame.square.core.event.Event.EventType;

/**
 * @author Rico Schrage
 *
 * Emits events to all event-listeners.
 * <br>
 * Event can be received via annotation or {@link EventListener#handleEvent(Event, Object...)}.
 */
public class EventHandler {

    /** List of all registered {@link EventListener}'s */
    private final Map<EventListener, List<EventType>> eventHandlerMap;

    /** Map of all cached methods */
    private final Map<EventListener, Map<Event, Method>> cache;

    /**
     * Construct an event-emitter.
     */
    public EventHandler() {
        this.eventHandlerMap = new HashMap<>();
        this.cache = new HashMap<>();
    }

    /**
     * Convenience method to register an anonymous listener more comfortable.
     *
     * @param eventType event type the listener will listen to
     * @param eventListener listener
     */
    public void register(EventType eventType, EventListener eventListener) {
        this.register(eventListener, eventType);
    }

    /**
     * Register an {@link EventListener} for a specific event type.
     *
     * @param eventListener {@link EventListener}, which should get registered
     * @param type event types the listener will listen to
     */
    public void register(EventListener eventListener, EventType... type) {
        if (eventHandlerMap.containsKey(eventListener)) {
            Collections.addAll(eventHandlerMap.get(eventListener), type);
        }
        else {
            final List<Event.EventType> list = new ArrayList<>(EventType.values().length);
            Collections.addAll(list, type);
            this.eventHandlerMap.put(eventListener, list);
        }
    }

    /**
     * Unregister an {@link EventListener}.
     *
     * @param eventListener {@link EventListener}, which should get unregistered
     */
    public void unregister(EventListener eventListener, EventType... type) {
        if (eventHandlerMap.containsKey(eventListener)) {
            final List<EventType> list = eventHandlerMap.get(eventListener);
            if (list.size() == 1) {
                eventHandlerMap.remove(eventListener);
            }
            else {
                for (EventType aType : type) {
                    list.remove(aType);
                }
            }
        }
    }

    /**
     * Checks whether an {@link EventListener} is registered for a specific event type.
     *
     * @param eventListener listener, which should get checked.
     * @return true if the listener is already registered, false otherwise
     */
    public boolean isRegistered(EventListener eventListener, EventType eventType) {
        return eventHandlerMap.containsKey(eventListener)
                && eventHandlerMap.get(eventListener).contains(eventType);
    }

    /**
     * Emits an event to all listeners, which are registered for the specific event type. The events can either be received via {@link EventListener#handleEvent(Event, Object...)} or
     * with the help of annotations {@link EventRoute}.
     * <br>
     * Hint: Every event will only be delivered once!
     *
     * @param event type of the event
     * @param attachedObjects objects, which are related to the update.
     * @see Event
     */
    public void emitEvent(Event event, Object... attachedObjects) {
        for (Map.Entry<EventListener, List<EventType>> entry : eventHandlerMap.entrySet()) {
            final EventListener eventListener = entry.getKey();
            final boolean sendEvent = entry.getValue().contains(event.getType());
            if (sendEvent && !emitReflectionEvent(eventListener, event, attachedObjects)) {
                eventListener.handleEvent(event, attachedObjects);
            }
        }
    }

    /**
     * Helper method, which emits the give event to the appropriate annotated method.
     *
     * @param target recipient of the event
     * @param event {@link Event}
     * @param attached attached objects
     * @return true if an appropriate method has been found, false otherwise
     */
    private boolean emitReflectionEvent(final EventListener target, final Event event, final Object... attached) {
        if (cache.containsKey(target)) {
            Map<Event, Method> methodMap = cache.get(target);
            if (methodMap.containsKey(event)) {
                Method method = methodMap.get(event);
                try {
                    method.invoke(target, attached);
                    return true;
                }
                catch (IllegalAccessException | InvocationTargetException e) {
                    printReflectionError(e);
                }
                return false;
            }
        }
        else {
            cache.put(target, new EnumMap<Event, Method>(Event.class));
        }
        for (final Method method : target.getClass().getMethods()) {
            if (method.isAnnotationPresent(EventRoute.class) && method.getAnnotation(EventRoute.class).value() == event) {
                try {
                    method.setAccessible(true);
                    method.invoke(target, attached);
                    cache.get(target).put(event, method);
                    return true;
                }
                catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    printReflectionError(e);
                }
            }
        }
        return false;
    }

    /**
     * Printe an error message to the gdx logger when an reflection exception occurs.
     *
     * @param e Exception to be reported
     */
    private void printReflectionError(Exception e) {
        Gdx.app.error(getClass().getSimpleName(), "An " + e.getClass().getSimpleName() + " has occurred. Reason: " + e.getMessage(), e);
    }

}

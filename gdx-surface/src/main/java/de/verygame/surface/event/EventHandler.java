package de.verygame.surface.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Rico Schrage
 *
 * Emits events to all event-listeners.
 * <br>
 * Event can be received via annotation or {@link EventListener#handleEvent(Event, Object...)}.
 */
public class EventHandler {
    private static final Logger log = LoggerFactory.getLogger(EventHandler.class);

    /** List of all registered {@link EventRouteListener}'s */
    private final Map<EventRouteListener, List<EventType>> eventHandlerMap;

    /** Map of all cached methods */
    private final Map<EventRouteListener, Map<Event, Method>> cache;

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
    public void register(EventType eventType, EventRouteListener eventListener) {
        this.register(eventListener, eventType);
    }

    /**
     * Register an {@link EventListener} for a specific event type.
     *
     * @param eventListener {@link EventListener}, which should get registered
     * @param type event types the listener will listen to
     */
    public void register(EventRouteListener eventListener, EventType... type) {
        if (eventHandlerMap.containsKey(eventListener)) {
            Collections.addAll(eventHandlerMap.get(eventListener), type);
        }
        else {
            final List<EventType> list = new ArrayList<>(EventType.values().length);
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
     * @see CoreEvent
     */
    public void emitEvent(Event event, Object... attachedObjects) {
        for (Map.Entry<EventRouteListener, List<EventType>> entry : eventHandlerMap.entrySet()) {
            final EventRouteListener eventListener = entry.getKey();
            final boolean sendEvent = entry.getValue().contains(event.getType());
            if (sendEvent && !emitReflectionEvent(eventListener, event, attachedObjects)) {
                if (eventListener instanceof EventListener) {
                    ((EventListener)eventListener).handleEvent(event, attachedObjects);
                }
            }
        }
    }

    /**
     * Helper method, which emits the give event to the appropriate annotated method.
     *
     * @param target recipient of the event
     * @param event {@link CoreEvent}
     * @param attached attached objects
     * @return true if an appropriate method has been found, false otherwise
     */
    private boolean emitReflectionEvent(final EventRouteListener target, final Event event, final Object... attached) {
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
            cache.put(target, new HashMap<Event, Method>());
        }
        for (final Method method : target.getClass().getMethods()) {
            if (method.isAnnotationPresent(EventRoute.class) && method.getAnnotation(EventRoute.class).value() == event.getId()) {
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
        log.error("An " + e.getClass().getSimpleName() + " has occurred. Reason: " + e.getMessage(), e);
    }

}

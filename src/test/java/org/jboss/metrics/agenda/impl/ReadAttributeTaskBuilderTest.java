package org.jboss.metrics.agenda.impl;

import static org.jboss.metrics.agenda.TaskDefinition.TimeUnit.SECOND;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.jboss.dmr.ModelNode;
import org.jboss.metrics.agenda.Task;
import org.jboss.metrics.agenda.TaskBuilder;
import org.jboss.metrics.agenda.TaskDefinition;
import org.junit.Before;
import org.junit.Test;

public class ReadAttributeTaskBuilderTest {

    private TaskBuilder taskBuilder;

    @Before
    public void setUp() {
        taskBuilder = new ReadAttributeTaskBuilder();
    }

    @Test
    public void createTask() {
        TaskDefinition td = new TaskDefinition("/subsystem=datasources/data-source=ExampleDS/statistics=pool",
                "ActiveCount", 5, SECOND);

        Task task = taskBuilder.createTask(td);
        assertNotNull(task);

        ModelNode operation = task.getOperation();
        assertNotNull(operation);

        ModelNode expected = new ModelNode();
        expected.get("address").set("subsystem", "datasources");
        expected.get("address").set("data-source", "ExampleDS");
        expected.get("address").set("statistics", "pool");
        expected.get("operation").set("read-attribute");
        expected.get("name").set("ActiveCount");
        assertEquals(expected, operation);
    }
}
package io.file;

import io.file.data.AbstractPack;
import io.file.data.ApplicationLogPack;
import io.file.data.RequestLogPack;
import io.file.data.ServerLogPack;
import io.file.dto.RequestPackDTO;
import io.file.io.data.DataReader;
import io.file.io.data.DataWriter;
import io.file.service.AbstractLogService;
import org.junit.Test;

import static org.junit.Assert.*;

public class AbstractPackIOTest {
    private final long projectCode = 2022;
    private final int agentId = 02;
    private final long time = 23;
    private final String content = "Welcome to Whatap World";
    private final long line = 1200;

    private final int status = 404;
    private final long responseTime = 1234;

    @Test
    public void 애플리케이션_로그_객체를_바이트_배열로_전환하고_복원할_수_있다() {
        ApplicationLogPack before = new ApplicationLogPack(projectCode, agentId, time, content, line);
        DataWriter dataWriter = DataWriter.typeOfByteArray();
        before.write(dataWriter);
        byte[] bytes = dataWriter.toByteArray();
        DataReader dataReader = DataReader.typeOfByteArray(bytes);
        AbstractPack after = ApplicationLogPack.create(dataReader);

        assertEqualsAbstractPack(before, after);
        assertEqualsApplicationLogPack(before, after);
    }

    @Test
    public void 서버_로그_객체를_바이트_배열로_전환하고_복원할_수_있다() {
        String fileName = AbstractLogService.SERVER_FILE_NAME;
        ServerLogPack before = new ServerLogPack(projectCode, agentId, time, content, fileName, line);
        DataWriter dataWriter = DataWriter.typeOfByteArray();
        before.write(dataWriter);
        byte[] bytes = dataWriter.toByteArray();
        DataReader dataReader = DataReader.typeOfByteArray(bytes);
        AbstractPack after = ServerLogPack.create(dataReader);

        assertEqualsAbstractPack(before, after);
        assertEqualsServerLogPack(before, after);
    }

    @Test
    public void 와탭_서버로_전송되는_객체를_바이트_배열로_전환_하고_복원할_수_있다() {
        RequestLogPack before = RequestLogPack.valueOf(new RequestPackDTO(projectCode, agentId, time, status, responseTime));
        DataWriter dataWriter = DataWriter.typeOfByteArray();
        before.write(dataWriter);
        byte[] bytes = dataWriter.toByteArray();
        DataReader dataReader = DataReader.typeOfByteArray(bytes);
        AbstractPack after = RequestLogPack.create(dataReader);

        assertEqualsAbstractPack(before, after);
        assertEqualsRequestLogPack(before, after);
    }

    private static void assertEqualsAbstractPack(AbstractPack before, AbstractPack after) {
        assertNotNull(before);
        assertNotNull(after);

        assertEquals(before.getProjectCode(), after.getProjectCode());
        assertEquals(before.getAgentId(), after.getAgentId());
        assertEquals(before.getTime(), after.getTime());
    }

    private static void assertEqualsApplicationLogPack(AbstractPack before, AbstractPack after) {
        assertTrue(before instanceof ApplicationLogPack);
        assertTrue(after instanceof ApplicationLogPack);

        ApplicationLogPack beforeCasted = (ApplicationLogPack) before;
        ApplicationLogPack afterCasted = (ApplicationLogPack) after;

        assertEquals(beforeCasted.getContent(), afterCasted.getContent());
        assertEquals(beforeCasted.getLine(), afterCasted.getLine());
    }

    private static void assertEqualsServerLogPack(AbstractPack before, AbstractPack after) {
        assertTrue(before instanceof ServerLogPack);
        assertTrue(after instanceof ServerLogPack);

        ServerLogPack beforeCasted = (ServerLogPack) before;
        ServerLogPack afterCasted = (ServerLogPack) after;

        assertEquals(beforeCasted.getContent(), afterCasted.getContent());
        assertEquals(beforeCasted.getFileName(), afterCasted.getFileName());
        assertEquals(beforeCasted.getLine(), afterCasted.getLine());
    }

    private static void assertEqualsRequestLogPack(AbstractPack before, AbstractPack after) {
        assertTrue(before instanceof RequestLogPack);
        assertTrue(after instanceof RequestLogPack);

        RequestLogPack beforeCasted = (RequestLogPack) before;
        RequestLogPack afterCasted = (RequestLogPack) after;

        assertEquals(beforeCasted.getStatus(), afterCasted.getStatus());
        assertEquals(beforeCasted.getResponseTime(), afterCasted.getResponseTime());
    }
}

package com.github.hiroyuki_sato.digdag.plugin.mysql;

import com.google.common.base.Optional;
import io.digdag.spi.ImmutableTaskRequest;

import java.time.Instant;
import java.time.ZoneId;
import java.util.UUID;

import static com.github.hiroyuki_sato.digdag.plugin.mysql.ConfigUtils.newConfig;

public class OperatorTestingUtils
{
    public static ImmutableTaskRequest newTaskRequest()
    {
        return ImmutableTaskRequest.builder()
                .siteId(1)
                .projectId(2)
                .workflowName("wf")
                .revision(Optional.of("rev"))
                .taskId(3)
                .attemptId(4)
                .sessionId(5)
                .taskName("t")
                .lockId("l")
                .timeZone(ZoneId.systemDefault())
                .sessionUuid(UUID.randomUUID())
                .sessionTime(Instant.now())
                .createdAt(Instant.now())
                .config(newConfig())
                .localConfig(newConfig())
                .lastStateParams(newConfig())
                .build();
    }
}

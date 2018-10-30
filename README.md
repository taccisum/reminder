[![MIT Licence](https://badges.frapsoft.com/os/mit/mit.svg?v=103)](https://opensource.org/licenses/mit-license.php)
[![Build Status](https://www.travis-ci.org/taccisum/reminder.svg?branch=master)](https://www.travis-ci.org/taccisum/reminder)
[![codecov](https://codecov.io/gh/taccisum/reminder/branch/master/graph/badge.svg)](https://codecov.io/gh/taccisum/reminder)


# 项目简介

`reminder`是一个通用的提醒框架，旨在为企业应用中常见的对于一个提醒该`发给谁`、`发什么`、`如何发`的问题提供一个解决方案。

# 如何使用

## getting start

reminder提供了`starter`，方便spring boot用户快速进行集成。

### 引入依赖

### 创建channel
```java
@Component
public class ConsoleChannel implements Channel {
    public String code() {
        return "CONSOLE";
    }

    public String name() {
        return "console channel";
    }

    public void send(Target target, Message message, Object[] objects) {
        System.out.println(String.format("send message \"%s\" via console channel", message.getBody()));
    }
}
```

### 创建message builder

```java
@Component
public class FooMessageBuilder extends TemaplteMessageBuilder {
    public FooMessageBuilder(Metadata metadata) {
        super(metadata);
    }

    public Message build(Target target, Subject subject, Object... objects) {
        MessageTemplate template = getTemplate();
        SimpleMessage message = new SimpleMessage();
        String body = template.getBody();
        body = body.replace("{id}", target.getId().toString());
        body = body.replace("{subject}", subject.serialized());

        message.setTopic(template.getTopic());
        message.setBody(body);

        return message;
    }

    public String code() {
        return "FOO";
    }
}
```

### 创建target selector

```java
@Component
public class FooTargetSelector implements TargetSelector {
    public String code() {
        return "FOO";
    }

    public List<Target> select(Object... objects) {
        List<Long> ids = (List<Long>) objects[0];
        ArrayList<Target> targets = new ArrayList<Target>();
        for (Long id : ids) {
            targets.add(new UserTarget(id));
        }
        return targets;
    }
}
```

### 添加message templates

**templates.ini**
```ini
[FOO]
topic=foo topic
body=target id: {id}, subject: {subject}
```

### 使用reminder发送提醒

```java
@Component
public class FooRunner implements ApplicationRunner {
    @Autowired
    private Reminder reminder;

    public void run(ApplicationArguments applicationArguments) throws Exception {
        reminder.remind("FOO", null, "CONSOLE", Arrays.asList(1L, 2L, 3L));
    }
}
```

### 启动应用

你可以看到控制台输出了如下信息
```text
send message "target id: 1, subject: 1" via console channel
send message "target id: 2, subject: 1" via console channel
send message "target id: 3, subject: 1" via console channel
```

## 新增一种提醒类型 

每新增一种提醒类型在reminder中至少需要新增一个`MessageBuilder`及一个`TargetSelector`。

`TargetSelector`描述了如何为该类型的提醒选择目标对象，通过新建一个类继承自`TargetSelector`并注册为spring bean即可。

`MessageBuilder`描述了如何为该类型的提醒构建需要发送的内容，通过新建一个类继承自`MessageBuilder`并注册为spring bean即可。
如果继承自`TemplateMessageBuilder`，则表示该message builder是从模板中构建模板的，可以从`Metadata`中获取到模板数据，默认实现是`IniMetadata`，即从ini文件中获取模板数据。

上述两个组件均实现了`Unique`接口，需要将其`code()`方法返回值指向该提醒的唯一code，之后发起提醒时会自动根据其code找到相应的组件进行处理。

## 指定channel进行发送

reminder支持多渠道 + 降级发送，使用`ChannelDescriptor`来描述这个过程。

你也可以实现自己的`ChannelDescriptor`，不过reminder已经提供了一个`StringChannelDescriptor`供使用。其规则如下：

- 使用字符串来描述channel
- 使用channel的code来映射到相应的channel实例
- 通过`,`号分隔需要同时发送的channel
- 通过`@`号描述channel链

示例：

- `A@B, C`：同时通过channel A和channel C发送提醒，如果通过A发送失败，则降级为通过B发送

## 使用facade类简化及语义化你的提醒调用[推荐]

`Reminder`提供了发起一个提醒的统一入口，但是由于不同类型提醒在业务上可能存在异构性，因此提供了一个Object类型的参数供各个提醒传入各自需要的自定义参数。但是这样一来，势必造成方法调用时的混乱。为了解决这个问题，建议通过一个facade类来封装各类型提醒的调用过程。

以在`getting start`中提到的`FOO`提醒为例

```java
@Service
public class RemindingServiceFacade {
    @Autowired
    private Reminder reminder;

    public void foo(Long... targetIds) {
        reminder.remind("FOO", null, "CONSOLE", Arrays.asList(targetIds));
    }
}
```

如此一来，在实际的业务代码中需要发起一个`FOO`类型的提醒时便简单多了

```java
// facade是RemindingServiceFacade的实例
facade.foo(1L, 2L, 3L);
```

# 示例项目



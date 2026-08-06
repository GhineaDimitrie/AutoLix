window.AutoLixChat = (function () {
    const STORAGE_KEY = 'autolix_chat_history';
    const STYLE_ID = 'autolix-chat-shared-styles';

    // Shared visual language for message bubbles/avatar/typing - injected once so both
    // the widget and the full /chat page render messages identically without copy-pasted CSS.
    function injectSharedStyles() {
        if (document.getElementById(STYLE_ID)) {
            return;
        }
        const style = document.createElement('style');
        style.id = STYLE_ID;
        style.textContent = `
            .autolix-chat-row {
                display: flex;
                align-items: flex-end;
                gap: 8px;
                animation: autolix-fade-in-up 0.25s ease-out;
            }
            .autolix-chat-row-user { justify-content: flex-end; }

            .autolix-chat-avatar {
                flex: 0 0 auto;
                width: 26px;
                height: 26px;
                border-radius: 50%;
                background: linear-gradient(135deg, #2fa4f5, #8a5cf6);
                color: white;
                display: flex;
                align-items: center;
                justify-content: center;
                font-size: 12px;
                box-shadow: 0 2px 8px rgba(122,92,246,0.45);
            }
            .autolix-chat-avatar img { width: 15px; height: 15px; }

            .autolix-chat-bubble {
                max-width: 78%;
                padding: 9px 13px;
                border-radius: 14px;
                font-size: 13.5px;
                line-height: 1.45;
                white-space: pre-wrap;
                word-break: break-word;
            }
            .autolix-chat-bubble-user {
                background: #2fa4f5;
                color: white;
                border-bottom-right-radius: 4px;
            }
            .autolix-chat-bubble-assistant {
                background: rgba(255,255,255,0.08);
                border: 1px solid rgba(255,255,255,0.08);
                color: #f0f0f0;
                border-bottom-left-radius: 4px;
            }
            .autolix-chat-bubble-error {
                background-color: rgba(231,76,60,0.85);
                color: white;
                font-size: 12px;
                margin: 0 auto;
            }
            .autolix-chat-bubble-greeting {
                background: rgba(138,92,246,0.14);
                border: 1px solid rgba(138,92,246,0.3);
                color: #f0f0f0;
            }

            .autolix-chat-col {
                display: flex;
                flex-direction: column;
                gap: 3px;
                max-width: 78%;
            }
            .autolix-chat-col-user { align-items: flex-end; }
            .autolix-chat-col-assistant { align-items: flex-start; }
            .autolix-chat-col .autolix-chat-bubble { max-width: 100%; }
            .autolix-chat-timestamp {
                font-size: 10.5px;
                color: rgba(255,255,255,0.4);
                padding: 0 4px;
            }

            .autolix-chat-cursor {
                display: inline-block;
                width: 2px;
                height: 12px;
                margin-left: 2px;
                background: currentColor;
                vertical-align: middle;
                animation: autolix-blink 0.8s step-end infinite;
            }

            .autolix-typing-row { display: flex; align-items: flex-end; gap: 8px; }
            .autolix-typing {
                display: flex;
                align-items: center;
                gap: 4px;
                padding: 11px 14px;
                border-radius: 14px;
                border-bottom-left-radius: 4px;
                background: rgba(255,255,255,0.08);
                border: 1px solid rgba(255,255,255,0.08);
            }
            .autolix-typing span {
                width: 6px;
                height: 6px;
                border-radius: 50%;
                background: #c9c9d6;
                animation: autolix-bounce 1.2s infinite ease-in-out;
            }
            .autolix-typing span:nth-child(2) { animation-delay: 0.15s; }
            .autolix-typing span:nth-child(3) { animation-delay: 0.3s; }

            @keyframes autolix-bounce {
                0%, 80%, 100% { transform: translateY(0); opacity: 0.4; }
                40% { transform: translateY(-4px); opacity: 1; }
            }
            @keyframes autolix-blink {
                0%, 100% { opacity: 1; }
                50% { opacity: 0; }
            }
            @keyframes autolix-fade-in-up {
                from { opacity: 0; transform: translateY(6px); }
                to { opacity: 1; transform: translateY(0); }
            }
        `;
        document.head.appendChild(style);
    }

    function loadHistory() {
        try {
            const raw = sessionStorage.getItem(STORAGE_KEY);
            return raw ? JSON.parse(raw) : [];
        } catch (e) {
            return [];
        }
    }

    function saveHistory(history) {
        sessionStorage.setItem(STORAGE_KEY, JSON.stringify(history));
    }

    function scrollToBottom(messagesEl) {
        messagesEl.scrollTop = messagesEl.scrollHeight;
    }

    function avatarEl() {
        const avatar = document.createElement('div');
        avatar.className = 'autolix-chat-avatar';
        avatar.innerHTML = '<img src="/images/ai-assistant-icon.svg" alt="AutoLix AI">';
        return avatar;
    }

    function formatTime() {
        return new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
    }

    // Renders one turn. animate=true does a typewriter reveal (used only for a freshly
    // arrived reply); history replay on page load always renders instantly (animate=false).
    function renderMessage(messagesEl, turn, animate) {
        const row = document.createElement('div');
        row.className = 'autolix-chat-row' + (turn.role === 'user' ? ' autolix-chat-row-user' : '');

        if (turn.role === 'assistant') {
            row.appendChild(avatarEl());
        }

        const col = document.createElement('div');
        col.className = 'autolix-chat-col autolix-chat-col-' + turn.role;

        const bubble = document.createElement('div');
        bubble.className = 'autolix-chat-bubble autolix-chat-bubble-' + turn.role;
        col.appendChild(bubble);

        if (turn.time) {
            const time = document.createElement('div');
            time.className = 'autolix-chat-timestamp';
            time.textContent = turn.time;
            col.appendChild(time);
        }

        row.appendChild(col);
        messagesEl.appendChild(row);

        if (!animate) {
            bubble.textContent = turn.content;
            scrollToBottom(messagesEl);
            return;
        }

        const text = turn.content;
        const cursor = document.createElement('span');
        cursor.className = 'autolix-chat-cursor';
        bubble.appendChild(cursor);

        const totalTicks = 55;
        const charsPerTick = Math.max(1, Math.ceil(text.length / totalTicks));
        let shown = 0;

        const timer = setInterval(function () {
            shown = Math.min(text.length, shown + charsPerTick);
            bubble.textContent = text.slice(0, shown);
            bubble.appendChild(cursor);
            scrollToBottom(messagesEl);
            if (shown >= text.length) {
                clearInterval(timer);
                cursor.remove();
            }
        }, 18);
    }

    function renderTyping(messagesEl) {
        const row = document.createElement('div');
        row.className = 'autolix-typing-row';
        row.appendChild(avatarEl());

        const dots = document.createElement('div');
        dots.className = 'autolix-typing';
        dots.innerHTML = '<span></span><span></span><span></span>';
        row.appendChild(dots);

        messagesEl.appendChild(row);
        scrollToBottom(messagesEl);
        return row;
    }

    function renderError(messagesEl, text) {
        const row = document.createElement('div');
        row.className = 'autolix-chat-row';
        const bubble = document.createElement('div');
        bubble.className = 'autolix-chat-bubble autolix-chat-bubble-error';
        bubble.textContent = text;
        row.appendChild(bubble);
        messagesEl.appendChild(row);
        scrollToBottom(messagesEl);
    }

    function renderGreeting(messagesEl, text) {
        const row = document.createElement('div');
        row.className = 'autolix-chat-row';
        row.appendChild(avatarEl());
        const bubble = document.createElement('div');
        bubble.className = 'autolix-chat-bubble autolix-chat-bubble-assistant autolix-chat-bubble-greeting';
        bubble.textContent = text;
        row.appendChild(bubble);
        messagesEl.appendChild(row);
    }

    // config: { messagesId, formId, inputId, greeting? } - the DOM ids of the surface
    // calling init, plus an optional greeting shown only when there's no history yet.
    // Both the widget and the full /chat page call this with their own ids.
    function init(config) {
        const messagesEl = document.getElementById(config.messagesId);
        const formEl = document.getElementById(config.formId);
        const inputEl = document.getElementById(config.inputId);

        if (!messagesEl || !formEl || !inputEl) {
            return;
        }

        injectSharedStyles();

        const history = loadHistory();
        if (history.length === 0 && config.greeting) {
            renderGreeting(messagesEl, config.greeting);
        }
        history.forEach(function (turn) {
            renderMessage(messagesEl, turn, false);
        });

        formEl.addEventListener('submit', function (e) {
            e.preventDefault();
            const text = inputEl.value.trim();
            if (!text) {
                return;
            }

            const userTurn = { role: 'user', content: text, time: formatTime() };
            history.push(userTurn);
            saveHistory(history);
            renderMessage(messagesEl, userTurn, false);
            inputEl.value = '';
            inputEl.disabled = true;

            const typingRow = renderTyping(messagesEl);

            fetch('/chat/ask', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ messages: history })
            })
                .then(function (res) {
                    return res.json().then(function (data) {
                        return { ok: res.ok, data: data };
                    });
                })
                .then(function (result) {
                    typingRow.remove();
                    if (result.ok) {
                        const assistantTurn = { role: 'assistant', content: result.data.reply, time: formatTime() };
                        history.push(assistantTurn);
                        saveHistory(history);
                        renderMessage(messagesEl, assistantTurn, true);
                    } else {
                        renderError(messagesEl, result.data.reply || 'Something went wrong.');
                    }
                })
                .catch(function () {
                    typingRow.remove();
                    renderError(messagesEl, 'Could not reach the AutoLix assistant. Check your connection and try again.');
                })
                .finally(function () {
                    inputEl.disabled = false;
                    inputEl.focus();
                });
        });
    }

    return { init: init };
})();

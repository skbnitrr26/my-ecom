import React, { useEffect, useRef, useState } from "react";
import { useAppDispatch, useAppSelector } from "../../../Redux Toolkit/Store";
import { chatBot } from "../../../Redux Toolkit/Customer/AiChatBotSlice";
import { Button, IconButton } from "@mui/material";
import SendIcon from "@mui/icons-material/Send";
import PromptMessage from "./PromptMessage";
import ResponseMessage from "./ResponseMessage";
import CloseIcon from "@mui/icons-material/Close";

interface ChatBotProps {
  handleClose: (e: any) => void;
  productId?: number;
}

const ChatBot = ({ handleClose, productId }: ChatBotProps) => {
  const dispatch = useAppDispatch();
  const [prompt, setPrompt] = useState("");
  const chatContainerRef = useRef<HTMLDivElement>(null);
  const { aiChatBot, user } = useAppSelector((store) => store);

  const handleGivePrompt = (e?: any) => {
    if (e) e.stopPropagation();
    if (!prompt.trim()) return; // ignore empty
    dispatch(
      chatBot({
        prompt: { prompt },
        productId,
        userId: user.user?.id || null,
      })
    );
    setPrompt(""); // clear after sending
  };

  const handleKeyDown = (e: React.KeyboardEvent<HTMLTextAreaElement>) => {
    if (e.key === "Enter" && !e.shiftKey) {
      e.preventDefault(); // prevent newline
      handleGivePrompt();
    }
  };

  useEffect(() => {
    chatContainerRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [aiChatBot.messages]);

  return (
    <div className="rounded-lg">
      <div className="w-full lg:w-[40vw] h-[82vh] shadow-2xl bg-white z-50 rounded-lg">
        <div className="h-[12%] flex justify-between items-center px-5 bg-slate-100 rounded-t-lg">
          <div className="flex items-center gap-3">
            <h1 className="logo">Shumit Market</h1>
            <p>Assistant</p>
          </div>
          <IconButton onClick={handleClose} color="primary">
            <CloseIcon />
          </IconButton>
        </div>

        <div className="h-[78%] p-5 flex flex-col overflow-y-auto custom-scrollbar">
          <p>
            Welcome to Shumit Market Ai Assistant, you can{" "}
            {productId
              ? `query about this product: ${productId}`
              : "query about your cart and order history here"}
          </p>

          {aiChatBot.messages.map((item, index) =>
            item.role === "user" ? (
              <div ref={chatContainerRef} className="self-end" key={index}>
                <PromptMessage message={item.message} index={index} />
              </div>
            ) : (
              <div ref={chatContainerRef} className="self-start" key={index}>
                <ResponseMessage message={item.message} />
              </div>
            )
          )}

          {aiChatBot.loading && <p>Fetching data...</p>}
        </div>

        <div className="h-[10%] flex items-center">
          <textarea
            placeholder="Type your message..."
            value={prompt}
            onChange={(e) => setPrompt(e.target.value)}
            onKeyDown={handleKeyDown} // ✅ Enter sends / Shift+Enter newline
            className="rounded-bl-lg p-3 h-full w-full resize-none bg-slate-100 border-none outline-none"
          />
          <Button
            sx={{ borderRadius: "0 0 0.5rem 0" }}
            className="h-full"
            onClick={handleGivePrompt}
            variant="contained"
          >
            <SendIcon />
          </Button>
        </div>
      </div>
    </div>
  );
};

export default ChatBot;

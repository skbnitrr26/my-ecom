import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { api } from "../../config/Api";

// Define message type
interface ChatMessage {
  message: string;
  role: "user" | "assistant";
}

// Define the state type
interface AiChatBotState {
  response: string | null;
  loading: boolean;
  error: string | null;
  messages: ChatMessage[];
}

const initialState: AiChatBotState = {
  response: null,
  loading: false,
  error: null,
  messages: []
};

// Async thunk to send prompt to backend
export const chatBot = createAsyncThunk<
  { message: string; role: "assistant" },
  { prompt: { prompt: string }; productId?: number; userId?: number | null }
>(
  "aiChatBot/generateResponse",
  async ({ prompt, productId, userId }, { rejectWithValue }) => {
    try {
      const response = await api.post("/ai/chat", prompt, {
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${localStorage.getItem("jwt")}`
        },
        params: {
          userId,
          productId,
        },
      });
      return response.data;
    } catch (error: any) {
      return rejectWithValue(
        error.response?.data?.message || "Failed to generate chatbot response"
      );
    }
  }
);

// Slice
const aiChatBotSlice = createSlice({
  name: "aiChatBot",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(chatBot.pending, (state, action) => {
        state.loading = true;
        state.error = null;

        // ✅ Only push the plain text message (not productId)
        const userPrompt: ChatMessage = {
          message: action.meta.arg.prompt.prompt, // clean text only
          role: "user",
        };

        state.messages = [...state.messages, userPrompt];
      })
      .addCase(chatBot.fulfilled, (state, action) => {
        state.loading = false;
        state.response = action.payload.message;

        const assistantMessage: ChatMessage = {
          message: action.payload.message,
          role: "assistant",
        };

        state.messages = [...state.messages, assistantMessage];
      })
      .addCase(chatBot.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      });
  },
});

export default aiChatBotSlice.reducer;
